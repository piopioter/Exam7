package com.example.personinfo.position.controllers;

import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.repositories.EmployeeRepository;
import com.example.personinfo.position.commands.AssignPositionCommand;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.repositories.PositionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private PositionRepository positionRepository;
    @SpyBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper om;


    @Test
    @WithMockUser
    public void shouldReturnPositionsWithEmployeeIdOne() throws Exception {
        //given
        Employee e1 = new Employee();
        Employee e2 = new Employee();
        employeeRepository.save(e1);
        employeeRepository.save(e2);
        List<Position> positions = List.of(
                new Position("Mechanic", LocalDate.parse("2022-01-01"), LocalDate.parse("2024-01-01"), BigDecimal.valueOf(8000), e1),
                new Position("Programmer", LocalDate.parse("2025-01-01"), LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), e1),
                new Position("Plumber", LocalDate.parse("2025-01-01"), LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), e1)
        );
        positionRepository.saveAllAndFlush(positions);
        //when
        mockMvc.perform(get("/api/v1/positions/byEmployee/{id}", 1L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mechanic"))
                .andExpect(jsonPath("$[0].startDate").value("2022-01-01"))
                .andExpect(jsonPath("$[0].endDate").value("2024-01-01"))
                .andExpect(jsonPath("$[0].salary").value(8000))
                .andExpect(jsonPath("$[1].name").value("Programmer"))
                .andExpect(jsonPath("$[1].startDate").value("2025-01-01"))
                .andExpect(jsonPath("$[1].endDate").value("2029-01-01"))
                .andExpect(jsonPath("$[1].salary").value(24000));

        //then
        verify(positionRepository, times(1)).findAllByEmployeeId(1L);
    }


    @Test
    public void shouldReturn401Unauthorized() throws Exception {
        //when
        mockMvc.perform(get("/api/v1/positions/byEmployee/{id}", 1L))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnConflictInAssignMethod() throws Exception {
        //given
        Employee e1 = new Employee();
        employeeRepository.save(e1);
        positionRepository.save(new Position("Mechanic", LocalDate.parse("2022-01-01"),
                LocalDate.parse("2027-01-01"), BigDecimal.valueOf(8000), e1));
        AssignPositionCommand c = new AssignPositionCommand("Programmer", LocalDate.parse("2027-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 1L);
        //when
        mockMvc.perform(post("/api/v1/positions/assign")
                .content(om.writeValueAsString(c))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(
                        "Position date overlaps with existing employee position"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void shouldAssignPosition() throws Exception {
        //given
        Employee e1 = new Employee();
        employeeRepository.save(e1);
        AssignPositionCommand c = new AssignPositionCommand("Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 1L);
        //when
        mockMvc.perform(post("/api/v1/positions/assign")
                .content(om.writeValueAsString(c))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programmer"))
                .andExpect(jsonPath("$.employee.id").value(e1.getId()));

        //then
        verify(positionRepository, times(1)).save(any(Position.class));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundWhenEmployeeDoesNotExist() throws Exception {
        //given
        Employee e1 = new Employee("Jan", "Kowalski", "97012303195", 170.0, 80.5,
                "jan@wp.pl", LocalDate.parse("2022-02-02"), "Mechanic", BigDecimal.valueOf(5000));
        employeeRepository.saveAndFlush(e1);
        AssignPositionCommand command = new AssignPositionCommand("Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 2L);
        //when
        mockMvc.perform(post("/api/v1/positions/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found entity with id: 2"));
    }

    @Test
    @WithMockUser
    public void shouldDeletePosition() throws Exception {
        //given
        Position p1 = new Position(
                "Programmer", LocalDate.parse("2022-01-01"), LocalDate.parse("2024-01-01"), BigDecimal.valueOf(8000));
        positionRepository.saveAndFlush(p1);
        //when
        mockMvc.perform(delete("/api/v1/positions/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status").value("1"));
        //then
        verify(positionRepository, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser
    public void shouldReturnNotFoundDeletePosition() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/positions/{id}", 1L))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message").value("Not found entity to delete with id: 1"));
    }


}
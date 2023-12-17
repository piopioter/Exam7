package com.example.personinfo.position.controllers;

import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.repositories.EmployeeRepository;
import com.example.personinfo.position.commands.CreatePositionCommand;
import com.example.personinfo.position.commands.PositionAssignCommand;
import com.example.personinfo.position.commands.UpdatePositionCommand;
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
        verify(positionRepository, times(1)).findAllByEmployee_Id(1L);
    }


    @Test
    public void shouldReturn401Unauthorized() throws Exception {
        //when
        mockMvc.perform(get("/api/v1/positions/byEmployee/{id}", 1L))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser
    public void shouldReturnBadRequestInCreateMethod() throws Exception {
        //given
        positionRepository.save(new Position("Mechanic", LocalDate.parse("2022-01-01"),
                LocalDate.parse("2027-01-01"), BigDecimal.valueOf(8000)));
        CreatePositionCommand c = new CreatePositionCommand("Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000));
        //when
        mockMvc.perform(post("/api/v1/positions")
                .content(om.writeValueAsString(c))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "Position date  overlaps with existing position"));

    }

    @Test
    @WithMockUser
    public void shouldCreatePosition() throws Exception {
        //given
        CreatePositionCommand c = new CreatePositionCommand("Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000));
        //when
        mockMvc.perform(post("/api/v1/positions")
                .content(om.writeValueAsString(c))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Programmer"));

        //then
        verify(positionRepository, times(1)).save(any(Position.class));
    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAssignPositionWithRoleAdmin() throws Exception {
        //given
        Employee e1 = new Employee("Jan", "Kowalski", "97012303195", 170.0, 80.5,
                "jan@wp.pl", LocalDate.parse("2022-02-02"), "Mechanic", BigDecimal.valueOf(5000));
        employeeRepository.saveAndFlush(e1);
        when(positionRepository.existsById(1L)).thenReturn(true);
        PositionAssignCommand command = new PositionAssignCommand(1L, "Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 1L);
        //when
        mockMvc.perform(put("/api/v1/positions/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Programmer"))
                .andExpect(jsonPath("$.startDate").value("2025-01-01"))
                .andExpect(jsonPath("$.endDate").value("2029-01-01"))
                .andExpect(jsonPath("$.salary").value(24000))
                .andExpect(jsonPath("$.employee.id").value(e1.getId()));
        //then
        verify(positionRepository, times(1)).save(any(Position.class));
    }

    @Test
    @WithMockUser(roles = "IMPORTER")
    public void shouldReturn403UsingImporterRoleToAssignPosition() throws Exception {
        //when
        mockMvc.perform(put("/api/v1/positions/employee"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundWhenEmployeeDoesNotExist() throws Exception {
        //given
        Employee e1 = new Employee("Jan", "Kowalski", "97012303195", 170.0, 80.5,
                "jan@wp.pl", LocalDate.parse("2022-02-02"), "Mechanic", BigDecimal.valueOf(5000));
        employeeRepository.saveAndFlush(e1);
        PositionAssignCommand command = new PositionAssignCommand(1L, "Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 2L);
        //when
        mockMvc.perform(put("/api/v1/positions/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found entity with id: 2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundWhenPositionDoesNotExist() throws Exception {
        //given
        Employee e1 = new Employee("Jan", "Kowalski", "97012303195", 170.0, 80.5,
                "jan@wp.pl", LocalDate.parse("2022-02-02"), "Mechanic", BigDecimal.valueOf(5000));
        employeeRepository.saveAndFlush(e1);
        PositionAssignCommand command = new PositionAssignCommand(2L, "Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000), 1L);
        //when
        mockMvc.perform(put("/api/v1/positions/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found entity to assign with id: 2"));
    }

    @Test
    @WithMockUser
    public void shouldUpdatePosition() throws Exception {
        //given
        UpdatePositionCommand command = new UpdatePositionCommand(1L, "Programmer",
                LocalDate.parse("2025-01-01"), LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000));
        when(positionRepository.existsById(1L)).thenReturn(true);

        //when
        mockMvc.perform(put("/api/v1/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Programmer"))
                .andExpect(jsonPath("$.startDate").value("2025-01-01"))
                .andExpect(jsonPath("$.endDate").value("2029-01-01"))
                .andExpect(jsonPath("$.salary").value(24000));
        //then
        verify(positionRepository, times(1)).save(any(Position.class));

    }

    @Test
    @WithMockUser
    public void shouldReturnNotFoundWhenPositionToUpdateDoesNotExist() throws Exception {
        //given
        UpdatePositionCommand command = new UpdatePositionCommand(2L, "Programmer", LocalDate.parse("2025-01-01"),
                LocalDate.parse("2029-01-01"), BigDecimal.valueOf(24000));
        //when
        mockMvc.perform(put("/api/v1/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found entity to update with id: 2"));
    }


    @Test
    @WithMockUser
    public void shouldDeletePosition() throws Exception {
        //when
        when(positionRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/positions/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status").value("Deleted position with id: 1"));
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
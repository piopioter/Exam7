package com.example.personinfo.people.controllers;

import com.example.personinfo.people.commands.*;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Retiree;
import com.example.personinfo.people.models.Student;
import com.example.personinfo.people.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper om;


    @Test
    @WithMockUser
    public void shouldGetAllPersonByCriteria() throws Exception {
        //given
        Person e1 = new Employee("Jan", "Kowalski", "97012303195", 170.0, 80.5,
                "jan@wp.pl", LocalDate.parse("2022-02-02"), "Mechanic", BigDecimal.valueOf(5000));
        Person r1 = new Retiree("Jan", "Gil", "97012403194",
                180.0, 100.0, "jan@wp.pl", BigDecimal.valueOf(2400), 30);
        Person s1 = new Student("Kuba", "Dworrski", "47385946104", 190.7, 95.0,
                "kuba@o2.com", "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(5000));
        personRepository.saveAllAndFlush(List.of(e1, r1, s1));
        //when
        mockMvc.perform(get("/api/v1/people")
                .param("firstName", "Jan")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].pesel").value("97012303195"))
                .andExpect(jsonPath("$.content[1].firstName").value("Jan"))
                .andExpect(jsonPath("$.content[1].pesel").value("97012403194"));
        //then
        verify(personRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddPerson() throws Exception {
        //given
        CreatePersonCommand c1 = new CreateStudentCommand("Student", "Jan", "Kowalski",
                "97012303195", 165.0, 68.9, "jan@wp.pl", "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(6000));
        //when
        mockMvc.perform(post("/api/v1/people")
                .content(om.writeValueAsString(c1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.pesel").value("97012303195"))
                .andExpect(jsonPath("$.studyField").value("Philosophy"));

        //then
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    @WithMockUser(roles = "IMPORTER")
    public void shouldReturn403StatusInCreateMethodWithImporterRole() throws Exception {
        //when
        mockMvc.perform(post("/api/v1/people"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void shouldReturn403StatusInCreateMethodWithEmployeeRole() throws Exception {
        //when
        mockMvc.perform(post("/api/v1/people"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    public void shouldUpdatePersonWithAdminRole() throws Exception {
        //given
        Person p1 = new Student("Jan", "Kowalski", "97012303195", 165.0, 68.9,
                "jan@wp.pl", "Ignacego", LocalDate.parse("2022-02-02"),
                "Philosophy", BigDecimal.valueOf(6000));
        personRepository.saveAndFlush(p1);
        UpdatePersonCommand c1 = new UpdateStudentCommand("Student", 1L, "Jan", "Kowalski",
                "97012303195", 180.0, 68.9, "jan@wp.pl", 0L, "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(6000));
        //when
        mockMvc.perform(put("/api/v1/people")
                .content(om.writeValueAsString(c1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.pesel").value("97012303195"))
                .andExpect(jsonPath("$.height").value(180.0))
                .andExpect(jsonPath("$.studyField").value("Philosophy"));
        //then
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNotFoundWhenPersonIdDoesNotExist() throws Exception {
        //given
        Person p1 = new Student("Jan", "Kowalski", "97012303195", 165.0, 68.9,
                "jan@wp.pl", "Ignacego", LocalDate.parse("2022-02-02"),
                "Philosophy", BigDecimal.valueOf(6000));
        personRepository.saveAndFlush(p1);
        UpdatePersonCommand c1 = new UpdateStudentCommand("Student", 2L, "Jan", "Kowalski",
                "97012303195", 165.0, 68.9, "jan@wp.pl", 0L, "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(6000));
        //when
        mockMvc.perform(put("/api/v1/people")
                .content(om.writeValueAsString(c1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found entity to update with id: 2"));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnConflictStatusWhenVersionIsIncorrect() throws Exception {
        //given
        UpdatePersonCommand c1 = new UpdateStudentCommand("Student", 1L, "Jan", "Kowalski",
                "97012303195", 165.0, 68.9, "jan@wp.pl", 1L, "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(6000));
        Person p1 = new Student("Jan", "Kowalski", "97012303195", 165.0, 68.9,
                "jan@wp.pl", "Ignacego", LocalDate.parse("2022-02-02"),
                "Philosophy", BigDecimal.valueOf(6000));
        personRepository.saveAndFlush(p1);
        //when
        mockMvc.perform(put("/api/v1/people")
                .content(om.writeValueAsString(c1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(
                        "This entity has been modified by another entity"));
    }

    @Test
    @WithMockUser(roles = "IMPORTER")
    public void shouldReturn403StatusWithImporterRole() throws Exception {
        //when
        mockMvc.perform(put("/api/v1/people"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void shouldReturn403StatusWithEmployeeRole() throws Exception {
        //when
        mockMvc.perform(put("/api/v1/people"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }





}
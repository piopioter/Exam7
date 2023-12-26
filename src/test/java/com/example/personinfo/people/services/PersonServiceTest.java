package com.example.personinfo.people.services;

import com.example.personinfo.people.commands.UpdatePersonCommand;
import com.example.personinfo.people.commands.UpdateStudentCommand;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Student;
import com.example.personinfo.people.repositories.PersonRepository;
import org.apache.catalina.Executor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @SpyBean
    private PersonService personService;
    @Autowired
    private ModelMapper modelMapper;
    @SpyBean
    private PersonRepository personRepository;


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdatePersonWithOptimisticLocking() throws Exception {
        //given
        Person s1 = new Student("Jan", "Kowalski", "97012303195", 165.0, 68.9,
                "jan@wp.pl", "Ignacego", LocalDate.parse("2022-02-02"),
                "Philosophy", BigDecimal.valueOf(6000));
        UpdatePersonCommand c1 = new UpdateStudentCommand("Student", 1L, "Jan", "Kowalski",
                "97012303195", 200.0, 68.9, "jan@wp.pl", 0L, "Ignacego",
                LocalDate.parse("2022-02-02"), "Philosophy", BigDecimal.valueOf(7000));
        personService.save(s1);
        assertEquals(0,s1.getVersion());
        CountDownLatch latch = new CountDownLatch(2);
        PersonThread p1 = new PersonThread(c1, latch);
        PersonThread p2 = new PersonThread(c1, latch);
        //when
        p1.start();
        p2.start();
        latch.await();
        Person person = personRepository.findById(s1.getId()).orElseThrow();

        //then
        assertAll(
                () -> assertTrue(p1.hasObjectOptimisticLockingFailure() || p2.hasObjectOptimisticLockingFailure()),
                () -> assertEquals(1, person.getVersion()),
                () -> assertEquals(200.0, person.getHeight()),
                () -> verify(personService, times(2)).update(any(Person.class))
        );

    }

    class PersonThread extends Thread {
        private UpdatePersonCommand command;
        private CountDownLatch countDownLatch;
        private Class<?> exception;


        public PersonThread(UpdatePersonCommand command, CountDownLatch countDownLatch) {
            this.command = command;
            this.countDownLatch = countDownLatch;
        }

        boolean hasObjectOptimisticLockingFailure() {
            return this.exception == ObjectOptimisticLockingFailureException.class;
        }

        @Override
        public void run() {
            try {
                personService.update(modelMapper.map(command, Student.class));
            } catch (Exception e) {
                exception = e.getClass();
            } finally {
                countDownLatch.countDown();
            }
        }
    }














}
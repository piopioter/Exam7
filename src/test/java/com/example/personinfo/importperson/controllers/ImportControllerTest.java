package com.example.personinfo.importperson.controllers;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.models.LockTable;
import com.example.personinfo.importperson.repositories.LockRepository;
import com.example.personinfo.importperson.services.ImportService;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Student;
import com.example.personinfo.people.repositories.EmployeeRepository;
import com.example.personinfo.people.repositories.PersonRepository;
import com.example.personinfo.people.repositories.RetireeRepository;
import com.example.personinfo.people.repositories.StudentRepository;
import jakarta.validation.constraints.AssertTrue;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Spy;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ImportControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @SpyBean
    private ImportService importService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldUploadFile() throws Exception {
        //given
        String csv = "Employee,Anna,Nowak,8602145218,170,55.0,anna@wp.pl,2022-01-01,Developer,8000\n" +
                "Student,Jan,Kowalski,97012303195,180,80,jan@example.com,Ignacego,2022-02-02,Philosophy,3000\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv",
                csv.getBytes());
        //when
        mockMvc.perform(multipart("/api/v1/import")
                .file(file))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fileName").value("test.csv"));


    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnImportStatusDuringFileUpload() throws Exception {
        //given
        String csv = "Employee,Anna,Nowak,8602145218,170,55.0,anna@wp.pl,2022-01-01,Developer,8000\n" +
                "Student,Jan,Kowalski,97012303195,180,80,jan@example.com,Ignacego,2022-02-02,Philosophy,3000\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv",
                csv.getBytes());

        //when
        mockMvc.perform(multipart("/api/v1/import")
                .file(file))
                .andDo(print())
                .andExpect(status().isAccepted());

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() ->
                mockMvc.perform(get("/api/v1/import/1/status").with(user("user").roles("ADMIN")))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").value("COMPLETED"))
                        .andExpect(jsonPath("$.processedRows").value(2))
        );

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturn409StatusWhenImportInProgress() throws Exception {
        //given
        String csv = "Employee,Anna,Nowak,8602145218,170,55.0,anna@wp.pl,2022-01-01,Developer,8000\n" +
                "Student,Jan,Kowalski,97012303195,180,80,jan@example.com,Ignacego,2022-02-02,Philosophy,3000\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv",
                csv.getBytes());

        //when
        mockMvc.perform(multipart("/api/v1/import")
                .file(file))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();

        mockMvc.perform(multipart("/api/v1/import")
                .file(file))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Another import in progress"));
        //then
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnBadRequestWhenIncorrectTypeFile() throws Exception {
        //given
        String csv = "Employee,Anna,Nowak,8602145218,170,55.0,anna@wp.pl,2022-01-01,Developer,8000\n" +
                "Student,Kowalski,97012303195,180,80,jan@example.com,Ignacego,2022-02-02,Philosophy,3000\n";

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "image/jpg",
                csv.getBytes());
        //when
        mockMvc.perform(multipart("/api/v1/import")
                .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("upload.file: Invalid type file"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void shouldReturn403StatusForEmployeeRole() throws Exception {
        //when
        mockMvc.perform(multipart("/api/v1/import"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnStatusWhenImportInProgress() throws Exception {
        //given
        String csv = "Employee,Anna,Nowak,8602145218,170,55.0,anna@wp.pl,2022-01-01,Developer,8000\n" +
                "Student,Jan,Kowalski,97012303195,180,80,jan@example.com,Ignacego,2022-02-02,Philosophy,3000\n";

       CountDownLatch latch = new CountDownLatch(2);
       ImportThread i1 = new ImportThread(latch,csv);
       ImportThread i2 = new ImportThread(latch,csv);
        //when
        i1.start();
        i2.start();
        latch.await();

        //then
        assertAll(
                () -> assertTrue(i1.hasImportAlreadyInProgressException() || i2.hasImportAlreadyInProgressException())
        );
    }


    class ImportThread extends Thread {
        private CountDownLatch countDownLatch;
        private Class<?> exception;
        private String csv;

        public ImportThread(CountDownLatch countDownLatch, String csv) {
            this.countDownLatch = countDownLatch;
            this.csv = csv;
        }

        boolean hasImportAlreadyInProgressException() {
            return this.exception == ImportAlreadyInProgressException.class;
        }

        @Override
        public void run() {
            try {
                importService.uploadFromCsvFile(new MockMultipartFile("file", "test.csv",
                        "text/csv", csv.getBytes()));
            } catch (Exception e) {
                exception = e.getClass();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

}



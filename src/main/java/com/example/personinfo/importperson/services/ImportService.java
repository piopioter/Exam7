package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Retiree;
import com.example.personinfo.people.models.Student;
import com.example.personinfo.people.services.EmployeeService;
import com.example.personinfo.people.services.RetireeService;
import com.example.personinfo.people.services.StudentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ImportService implements IImportService {

    private EmployeeService employeeService;
    private RetireeService retireeService;
    private StudentService studentService;
    private ImportStatus importStatus = new ImportStatus();
    private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public ImportService(EmployeeService employeeService, RetireeService retireeService,
                         StudentService studentService) {
        this.employeeService = employeeService;
        this.retireeService = retireeService;
        this.studentService = studentService;

    }

    @Override
    public void uploadFromCsvFile(MultipartFile file) {
        if (atomicBoolean.compareAndSet(false, true)) {
            importStatus.setCreationDate(LocalDateTime.now());
            importStatus.setStatus(StatusType.IN_PROGRESS);

            CompletableFuture.runAsync(() -> processFile(file))
                    .whenComplete((x, y) -> atomicBoolean.set(false));
        } else
            throw new ImportAlreadyInProgressException("Another import in progress");
    }

    @Override
    public ImportStatus getImportStatus() {
        return importStatus;
    }

    private void processFile(MultipartFile file) {
        int cnt = 0;
        String line;
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        ) {
            importStatus.setStartDate(LocalDateTime.now());
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                if ("Student".equalsIgnoreCase(type))
                    studentService.save(new Student(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                            Double.valueOf(parts[5]), parts[6], parts[7], LocalDate.parse(parts[8]), parts[9],
                            BigDecimal.valueOf(Double.parseDouble(parts[10]))));
                else if ("Employee".equalsIgnoreCase(type))
                    employeeService.save(new Employee(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                            Double.valueOf(parts[5]), parts[6], LocalDate.parse(parts[7]), parts[8],
                            BigDecimal.valueOf(Double.parseDouble(parts[9]))));
                else if ("Retiree".equalsIgnoreCase(type))
                    retireeService.save(new Retiree(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                            Double.valueOf(parts[5]), parts[6], BigDecimal.valueOf(Double.parseDouble(parts[7])),
                            Integer.valueOf(parts[8])));
                cnt++;
                importStatus.setProcessedRows(cnt);
            }
            importStatus.setStatus(StatusType.COMPLETED);
        } catch (IOException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ImportProcessingException("Error processing import ", e);
        }

    }
}

package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Component
public class FileProcessingService {

    private SimpleService simpleService;
    private PersonInsertService personInsertService;
    private LockService lockService;
    private JdbcTemplate jdbcTemplate;

    public FileProcessingService(SimpleService simpleService, PersonInsertService personInsertService,
                                 LockService lockService, JdbcTemplate jdbcTemplate) {
        this.simpleService = simpleService;
        this.personInsertService = personInsertService;
        this.lockService = lockService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Async
    @Transactional
    public void processFile(MultipartFile file, ImportStatus importStatus) {
        long cnt = 0;
        String line;
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        ) {
            importStatus.setStartDate(LocalDateTime.now());
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                personInsertService.insertPerson(type,parts,jdbcTemplate);
                cnt++;
                if (cnt % 500 == 0)
                    simpleService.updateStatus(importStatus, cnt);
            }
            importStatus.setStatus(StatusType.COMPLETED);
        } catch (FileNotFoundException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ResourceNotFoundException("File not found");
        } catch (IOException | DataIntegrityViolationException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ImportProcessingException("Error processing import ", e);
        } finally {
            lockService.unlock();
            simpleService.updateStatus(importStatus, cnt);
        }
    }

}


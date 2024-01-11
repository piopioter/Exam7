package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.ProcessPersonFactory;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.repositories.PersonRepository;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ImportService implements IImportService {

    private ImportRepository importRepository;
    private PersonRepository personRepository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public ImportService(ImportRepository importRepository, PersonRepository personRepository) {
        this.importRepository = importRepository;
        this.personRepository = personRepository;
    }


    @Override
    public String uploadFromCsvFile(MultipartFile file) {
        List<ImportStatus> imports = importRepository.findAllByStatus();
        if (!imports.isEmpty())
            throw new ImportAlreadyInProgressException("Another import in progress");

        ImportStatus importStatus = new ImportStatus();
        importStatus.setCreationDate(LocalDateTime.now());
        importStatus.setStatus(StatusType.IN_PROGRESS);

        ImportStatus status = importRepository.save(importStatus);
        CompletableFuture.runAsync(() -> processFile(file, status));

        return status.getId().toString();
    }

    @Override
    public ImportStatus getImportStatus(Long id) {
        return importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found  with id " + id));
    }


    @Transactional
    public void processFile(MultipartFile file, ImportStatus importStatus) {
        long cnt = 0;
        String line;
        List<Person> persons = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        ) {
            importStatus.setStartDate(LocalDateTime.now());
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                Person person = ProcessPersonFactory.createPerson(type, parts);
                persons.add(person);
                if (persons.size() == batchSize) {
                    personRepository.saveAll(persons);
                    cnt += persons.size();
                    persons.clear();
                    importStatus.setProcessedRows(cnt);
                    importRepository.save(importStatus);
                }
            }
            if (!persons.isEmpty()) {
                personRepository.saveAll(persons);
                cnt += persons.size();
                importStatus.setProcessedRows(cnt);
            }
            importStatus.setStatus(StatusType.COMPLETED);
        } catch (IOException | DataIntegrityViolationException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ImportProcessingException("Error processing import ", e);
        } finally {
            importRepository.save(importStatus);
        }
    }


}

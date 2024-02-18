package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.ProcessPersonFactory;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileProcessingService {

    private SimpleService simpleService;
    private ProcessPersonFactory personFactory;
    private PersonRepository personRepository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;
    @PersistenceContext
    private EntityManager entityManager;

    public FileProcessingService(SimpleService simpleService, ProcessPersonFactory personFactory,
                                 PersonRepository personRepository) {
        this.simpleService = simpleService;
        this.personFactory = personFactory;
        this.personRepository = personRepository;
    }

    @Async
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
                Person person = personFactory.createPerson(type, parts);
                persons.add(person);
                if (persons.size() == batchSize) {
                    cnt += persons.size();
                    personRepository.saveAllAndFlush(persons);
                    simpleService.updateStatus(importStatus, cnt);
                    entityManager.clear();
                    persons.clear();
                }
            }
            if (!persons.isEmpty()) {
                cnt += persons.size();
                personRepository.saveAllAndFlush(persons);
                simpleService.updateStatus(importStatus, cnt);
                entityManager.clear();
            }
            importStatus.setStatus(StatusType.COMPLETED);
        } catch (IOException | DataIntegrityViolationException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ImportProcessingException("Error processing import ", e);
        } finally {
            simpleService.updateStatus(importStatus, cnt);
        }
    }

}

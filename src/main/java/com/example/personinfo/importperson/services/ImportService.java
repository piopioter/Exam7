package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.ProcessPersonFactory;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ImportService implements IImportService {

    private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    private ImportStatus importStatus = new ImportStatus();
    private static final int BATCH_SIZE = 100;
    private ImportRepository importRepository;
    private PersonRepository personRepository;

    public ImportService(ImportRepository importRepository, PersonRepository personRepository) {
        this.importRepository = importRepository;
        this.personRepository = personRepository;
    }

    @Override
    public String uploadFromCsvFile(MultipartFile file) {
        ImportStatus status;
        if (atomicBoolean.compareAndSet(false, true)) {
            importStatus.setCreationDate(LocalDateTime.now());
            importStatus.setStatus(StatusType.IN_PROGRESS);
            status = importRepository.save(importStatus);

            CompletableFuture.runAsync(() -> processFile(file))
                    .whenComplete((x, y) -> atomicBoolean.set(false));

        } else
            throw new ImportAlreadyInProgressException("Another import in progress");

        return status.getId().toString();
    }

    @Override
    public ImportStatus getImportStatus(Long id) {
        ImportStatus status = importRepository.findById(id)
                .orElseThrow();
        return status.getStatus() == StatusType.IN_PROGRESS ? importStatus : status;
    }

    @Transactional
    public void processFile(MultipartFile file) {
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
                if (persons.size() == BATCH_SIZE) {
                    personRepository.saveAll(persons);
                    persons.clear();
                }
                cnt++;
                importStatus.setProcessedRows(cnt);
            }
            if (!persons.isEmpty())
                personRepository.saveAll(persons);
            importStatus.setStatus(StatusType.COMPLETED);
        } catch (IOException e) {
            importStatus.setStatus(StatusType.FAILED);
            throw new ImportProcessingException("Error processing import ", e);
        } finally {
            importRepository.save(importStatus);
        }

    }


}

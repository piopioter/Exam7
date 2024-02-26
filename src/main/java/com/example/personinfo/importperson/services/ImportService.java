package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class ImportService implements IImportService {

    private LockService lockService;
    private ImportRepository importRepository;
    private FileProcessingService fileProcessingService;



    public ImportService(LockService lockService, ImportRepository importRepository, FileProcessingService fileProcessingService) {
        this.lockService = lockService;
        this.importRepository = importRepository;
        this.fileProcessingService = fileProcessingService;
    }

    @Override
    @Transactional
    public String uploadFromCsvFile(MultipartFile file) {
        if (!lockService.lock())
            throw new ImportAlreadyInProgressException("Another import in progress");
        ImportStatus importStatus = new ImportStatus();
        importStatus.setCreationDate(LocalDateTime.now());
        importStatus.setStatus(StatusType.IN_PROGRESS);

        ImportStatus status = importRepository.save(importStatus);
        fileProcessingService.processFile(file, status);
        return status.getId().toString();

    }

    @Override
    public ImportStatus getImportStatus(Long id) {
        return importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));
    }


}

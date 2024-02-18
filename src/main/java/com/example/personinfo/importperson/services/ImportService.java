package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ImportService implements IImportService {

    private ImportRepository importRepository;
    private FileProcessingService fileProcessingService;
    private ReentrantLock lock;

    public ImportService(ImportRepository importRepository, FileProcessingService fileProcessingService, ReentrantLock lock) {
        this.importRepository = importRepository;
        this.fileProcessingService = fileProcessingService;
        this.lock = lock;
    }

    @Override
    public String uploadFromCsvFile(MultipartFile file) {
        lock.lock();
        try {
            if (isAnyProcessInProgress())
                throw new ImportAlreadyInProgressException("Another import in progress");
            ImportStatus importStatus = new ImportStatus();
            importStatus.setCreationDate(LocalDateTime.now());
            importStatus.setStatus(StatusType.IN_PROGRESS);

            ImportStatus status = importRepository.save(importStatus);
            fileProcessingService.processFile(file, status);
            return status.getId().toString();
        } finally {
            lock.unlock();
        }
    }

    private boolean isAnyProcessInProgress() {
        List<ImportStatus> imports = importRepository.findAllByStatusInProgress();
        return !imports.isEmpty();
    }

    @Override
    public ImportStatus getImportStatus(Long id) {
        return importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));
    }


}

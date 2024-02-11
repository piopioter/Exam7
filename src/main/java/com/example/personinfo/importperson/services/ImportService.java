package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.models.StatusType;
import com.example.personinfo.importperson.repositories.ImportRepository;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImportService implements IImportService {

    private ImportRepository importRepository;
    private FileProcessingService fileProcessingService;

    public ImportService(ImportRepository importRepository, FileProcessingService fileProcessingService) {
        this.importRepository = importRepository;
        this.fileProcessingService = fileProcessingService;
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
        fileProcessingService.processFile(file,status);

        return status.getId().toString();
    }

    @Override
    public ImportStatus getImportStatus(Long id) {
        return importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));
    }




}

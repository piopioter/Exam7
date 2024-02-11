package com.example.personinfo.importperson.controllers;

import com.example.personinfo.importperson.dto.ImportStatusDto;
import com.example.personinfo.importperson.dto.StatusDto;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.services.IImportService;
import com.example.personinfo.importperson.services.ImportService;
import com.example.personinfo.importperson.validations.ValidFile;
import com.example.personinfo.importperson.validations.ValidFileValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/v1/import")
@Validated
public class ImportController {

    private IImportService importService;
    private ModelMapper modelMapper;


    public ImportController(IImportService importService, ModelMapper modelMapper) {
        this.importService = importService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<StatusDto> upload(@Valid @ValidFile @RequestParam MultipartFile file) {
        String statusId = importService.uploadFromCsvFile(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new StatusDto(statusId,file.getOriginalFilename()));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ImportStatusDto> getStatus(@PathVariable("id") Long id) {
        ImportStatus importStatus = importService.getImportStatus(id);
        ImportStatusDto statusDto = modelMapper.map(importStatus, ImportStatusDto.class);
        return ResponseEntity.ok(statusDto);
    }
}

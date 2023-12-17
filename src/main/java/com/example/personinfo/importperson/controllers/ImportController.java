package com.example.personinfo.importperson.controllers;

import com.example.personinfo.importperson.dto.ImportStatusDto;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.services.ImportService;
import com.example.personinfo.importperson.validations.ValidFile;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/import")
@Validated
public class ImportController {

    private ImportService importService;
    private ModelMapper  modelMapper;

    public ImportController(ImportService importService, ModelMapper modelMapper) {
        this.importService = importService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam  @ValidFile MultipartFile file) {
        importService.uploadFromCsvFile(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Accepted file: " + file.getOriginalFilename());
    }

    @GetMapping("/status")
    public ResponseEntity<ImportStatusDto> getStatus(){
        ImportStatus importStatus = importService.getImportStatus();
        ImportStatusDto statusDto = modelMapper.map(importStatus, ImportStatusDto.class);

        return ResponseEntity.ok(statusDto);
    }
}

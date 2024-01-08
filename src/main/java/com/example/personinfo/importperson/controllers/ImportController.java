package com.example.personinfo.importperson.controllers;

import com.example.personinfo.importperson.dto.ImportStatusDto;
import com.example.personinfo.importperson.dto.StatusDto;
import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.services.ImportService;
import com.example.personinfo.importperson.validations.ValidFile;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<StatusDto> upload(@RequestParam  @ValidFile MultipartFile file) {
        String id = importService.uploadFromCsvFile(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new StatusDto("Accepted file: " + file.getOriginalFilename() , "Import id: " + id));
    }
    @GetMapping("/status/{id}")
    public ResponseEntity<ImportStatusDto> getStatus(@PathVariable("id") Long id){
        ImportStatus importStatus = importService.getImportStatus(id);
        ImportStatusDto statusDto = modelMapper.map(importStatus, ImportStatusDto.class);

        return ResponseEntity.ok(statusDto);
    }
}

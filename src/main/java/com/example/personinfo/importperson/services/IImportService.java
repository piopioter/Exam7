package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.models.ImportStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

public interface IImportService {

    String uploadFromCsvFile(MultipartFile file);

    ImportStatus getImportStatus(Long id);
}

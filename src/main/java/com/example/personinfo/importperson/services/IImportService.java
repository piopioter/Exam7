package com.example.personinfo.importperson.services;

import com.example.personinfo.importperson.models.ImportStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface IImportService {

   void uploadFromCsvFile(MultipartFile  file);
   ImportStatus getImportStatus();
}

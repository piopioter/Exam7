package com.example.personinfo.importperson.repositories;

import com.example.personinfo.importperson.models.ImportStatus;
import com.example.personinfo.importperson.services.ImportService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRepository extends JpaRepository<ImportStatus,Long> {
}

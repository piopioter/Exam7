package com.example.personinfo.importperson.repositories;

import com.example.personinfo.importperson.models.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImportRepository extends JpaRepository<ImportStatus,Long> {
    @Query("SELECT i FROM ImportStatus i WHERE i.status = 'IN_PROGRESS' ")
    List<ImportStatus> findAllByStatus();
}

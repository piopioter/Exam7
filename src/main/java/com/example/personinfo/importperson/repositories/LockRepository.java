package com.example.personinfo.importperson.repositories;

import com.example.personinfo.importperson.models.LockTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockRepository extends JpaRepository<LockTable, Long> {
}

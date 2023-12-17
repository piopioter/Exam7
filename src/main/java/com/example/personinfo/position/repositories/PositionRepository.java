package com.example.personinfo.position.repositories;

import com.example.personinfo.position.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Long> {
    List<Position> findAllByEmployee_Id(Long employeeId);
}

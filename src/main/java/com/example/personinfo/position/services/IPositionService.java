package com.example.personinfo.position.services;

import com.example.personinfo.position.models.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPositionService {

    List<Position> getAllByEmployeeId(Long employeeId);

    Position assignEmployee(Position position);

    Position create(Position position);

    Position update( Position position);

    void delete(Long positionId);


}

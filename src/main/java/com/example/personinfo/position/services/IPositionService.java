package com.example.personinfo.position.services;

import com.example.personinfo.position.models.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPositionService {

    List<Position> getAllByEmployeeId(Long employeeId);

    Position assignPosition(Position position);

    void delete(Long positionId);


}

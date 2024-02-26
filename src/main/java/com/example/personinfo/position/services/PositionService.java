package com.example.personinfo.position.services;

import com.example.personinfo.people.exceptions.DataConflictException;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.services.EmployeeService;
import com.example.personinfo.position.exceptions.InvalidDateRangeException;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.repositories.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PositionService implements IPositionService {

    private PositionRepository positionRepository;


    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> getAllByEmployeeId(Long employeeId) {
        return positionRepository.findAllByEmployeeId(employeeId);
    }

    @Override
    @Transactional
    public Position assignPosition(Position position) {
        List<Position> positions = getAllByEmployeeId(position.getEmployee().getId());
        if (!isPositionDateAvailable(positions, position))
            throw new DataConflictException("Position date overlaps with existing employee position");
        position.getEmployee().setCurrentSalary(position.getSalary());
        position.getEmployee().setCurrentPosition(position.getName());
        position.getEmployee().setEmploymentDate(position.getStartDate());
        return positionRepository.save(position);
    }

    @Override
    @Transactional
    public void delete(Long positionId) {
        positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found entity to delete with id: " + positionId));
        positionRepository.deleteById(positionId);
    }

    private boolean isPositionDateAvailable(List<Position> positions, Position position) {
        LocalDate start = position.getStartDate();
        LocalDate end = position.getEndDate();

        if (start.isAfter(end))
            throw new InvalidDateRangeException("Start date  must be before end date");

        for (Position p : positions) {
            if (start.isBefore(p.getEndDate().plusDays(1)) && end.isAfter(p.getStartDate())) {
                return false;
            }
        }
        return true;


    }
}

package com.example.personinfo.position.services;

import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.services.EmployeeService;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.repositories.PositionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PositionService implements IPositionService {

    private PositionRepository positionRepository;
    private EmployeeService employeeService;

    public PositionService(PositionRepository positionRepository, EmployeeService employeeService) {
        this.positionRepository = positionRepository;
        this.employeeService = employeeService;
    }


    @Override
    public List<Position> getAllByEmployeeId(Long employeeId) {
        return positionRepository.findAllByEmployee_Id(employeeId);

    }


    @Override
    public Position assignEmployee(Position position) {
        Employee employee = employeeService.get(position.getEmployee().getId());
        if (!positionRepository.existsById(position.getId()))
            throw new ResourceNotFoundException("Not found entity to assign with id: " + position.getId());
        employee.setCurrentPosition(position.getName());
        employee.setCurrentSalary(position.getSalary());
        employee.setEmploymentDate(position.getStartDate());
        return positionRepository.save(position);
    }

    @Override
    public Position create(Position position) {
        return positionRepository.save(position);
    }


    @Override
    public Position update(Position position) {
        if (!positionRepository.existsById(position.getId()))
            throw new ResourceNotFoundException("Not found entity to update with id: " + position.getId());
        return positionRepository.save(position);

    }

    @Override
    public void delete(Long positionId) {
        if (!positionRepository.existsById(positionId))
            throw new ResourceNotFoundException("Not found entity to delete with id: " + positionId);

        positionRepository.deleteById(positionId);

    }
}

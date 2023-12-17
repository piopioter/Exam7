package com.example.personinfo.people.services;

import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IPersonService<Employee> {

    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }


    public Employee get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found entity with id: " + id));
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }


}

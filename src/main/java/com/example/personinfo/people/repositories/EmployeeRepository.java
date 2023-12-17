package com.example.personinfo.people.repositories;

import com.example.personinfo.people.models.Employee;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmployeeRepository extends PersonGenericRepository<Employee> {

}

package com.example.personinfo.importperson.models;

import com.example.personinfo.people.models.Employee;
import com.example.personinfo.people.models.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeFactory implements PersonFactory{
    @Override
    public Person createPerson(String[] parts) {
        return new Employee(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                Double.valueOf(parts[5]), parts[6], LocalDate.parse(parts[7]), parts[8],
                BigDecimal.valueOf(Double.parseDouble(parts[9])));
    }
}

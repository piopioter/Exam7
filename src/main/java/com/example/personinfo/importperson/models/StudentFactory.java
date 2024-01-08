package com.example.personinfo.importperson.models;

import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentFactory implements PersonFactory{
    @Override
    public Person createPerson(String[] parts) {
        return new Student(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                Double.valueOf(parts[5]), parts[6], parts[7], LocalDate.parse(parts[8]), parts[9],
                BigDecimal.valueOf(Double.parseDouble(parts[10])));
    }
}

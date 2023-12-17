package com.example.personinfo.people.commands;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.models.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@PersonType(Employee.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateEmployeeCommand extends UpdatePersonCommand{
    @FutureOrPresent
    @NotNull
    private LocalDate employmentDate;
    @NotNull
    private String currentPosition;
    @Positive
    @NotNull
    private BigDecimal currentSalary;

    public UpdateEmployeeCommand() {
    }

    public UpdateEmployeeCommand(String type, Long id, String firstName, String lastName, String pesel,
                                 Double height, Double weight, String email, Long version, LocalDate employmentDate,
                                 String currentPosition, BigDecimal currentSalary) {
        super(type, id, firstName, lastName, pesel, height, weight, email, version);
        this.employmentDate = employmentDate;
        this.currentPosition = currentPosition;
        this.currentSalary = currentSalary;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }
}

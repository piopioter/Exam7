package com.example.personinfo.people.commands;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.models.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@PersonType(Employee.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEmployeeCommand extends CreatePersonCommand {

    @FutureOrPresent
    @NotNull
    private LocalDate employmentDate;
    @NotNull
    private String currentPosition;
    @Positive
    @NotNull
    private BigDecimal currentSalary;

    public CreateEmployeeCommand() {
    }

    public CreateEmployeeCommand(String type, String firstName, String lastName, String pesel,
                                 Double height, Double weight, String email, LocalDate employmentDate,
                                 String currentPosition, BigDecimal currentSalary) {
        super(type, firstName, lastName, pesel, height, weight, email);
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

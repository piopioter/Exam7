package com.example.personinfo.position.commands;

import com.example.personinfo.position.validations.NoDateOverlap;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.pl.PESEL;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoDateOverlap
public class CreatePositionCommand {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Positive
    @NotNull
    private BigDecimal salary;

    public CreatePositionCommand() {
    }

    public CreatePositionCommand(String name, LocalDate startDate, LocalDate endDate, BigDecimal salary) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}

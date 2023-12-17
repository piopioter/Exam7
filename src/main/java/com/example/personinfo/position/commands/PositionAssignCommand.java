package com.example.personinfo.position.commands;

import com.example.personinfo.position.validations.NoPositionDateOverlap;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoPositionDateOverlap
public class PositionAssignCommand {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Positive
    @NotNull
    private BigDecimal salary;
    @NotNull
    private Long employeeId;

    public PositionAssignCommand() {
    }

    public PositionAssignCommand(Long id, String name, LocalDate startDate, LocalDate endDate,
                                 BigDecimal salary, Long employeeId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
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

    public Long getEmployeeId() {
        return employeeId;
    }
}

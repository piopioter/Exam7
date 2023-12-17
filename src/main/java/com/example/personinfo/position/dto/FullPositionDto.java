package com.example.personinfo.position.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FullPositionDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal salary;
    private SimpleEmployeeDto employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public SimpleEmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(SimpleEmployeeDto employee) {
        this.employee = employee;
    }
}

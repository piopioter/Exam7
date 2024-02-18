package com.example.personinfo.people.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@DiscriminatorValue("Employee")
public class Employee extends Person implements Serializable {

    private static final long serialVersionUID = 42L;

    private LocalDate employmentDate;
    private String currentPosition;
    private BigDecimal currentSalary;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String pesel,
                    Double height, Double weight, String email,
                    LocalDate employmentDate, String currentPosition, BigDecimal currentSalary) {
        super(firstName, lastName, pesel, height, weight, email);
        this.employmentDate = employmentDate;
        this.currentPosition = currentPosition;
        this.currentSalary = currentSalary;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employmentDate, employee.employmentDate) && Objects.equals(currentPosition,
                employee.currentPosition) && Objects.equals(currentSalary, employee.currentSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employmentDate, currentPosition, currentSalary);
    }
}

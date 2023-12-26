package com.example.personinfo.people.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@DiscriminatorValue("Student")
public class Student extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String universityName;
    private LocalDate studyYear;
    private String studyField;
    private BigDecimal scholarshipAmount;

    public Student() {
    }

    public Student(String firstName, String lastName, String pesel,
                   Double height, Double weight, String email,
                   String universityName, LocalDate studyYear, String studyField,
                   BigDecimal scholarshipAmount) {
        super(firstName, lastName, pesel, height, weight, email);
        this.universityName = universityName;
        this.studyYear = studyYear;
        this.studyField = studyField;
        this.scholarshipAmount = scholarshipAmount;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public LocalDate getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(LocalDate studyYear) {
        this.studyYear = studyYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(universityName, student.universityName) &&
                Objects.equals(studyYear, student.studyYear) && Objects.equals(studyField, student.studyField) &&
                Objects.equals(scholarshipAmount, student.scholarshipAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), universityName, studyYear, studyField, scholarshipAmount);
    }
}

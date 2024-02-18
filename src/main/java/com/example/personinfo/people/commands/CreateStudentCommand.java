package com.example.personinfo.people.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
@JsonDeserialize
public class CreateStudentCommand extends CreatePersonCommand {

    @NotBlank
    private String universityName;
    @NotNull
    private LocalDate studyYear;
    @NotBlank
    private String studyField;
    @Positive
    private BigDecimal scholarshipAmount;

    public CreateStudentCommand() {
    }

    public CreateStudentCommand(String type, String firstName, String lastName, String pesel, Double height,
                                Double weight, String email, String universityName, LocalDate studyYear,
                                String studyField, BigDecimal scholarshipAmount) {
        super(type, firstName, lastName, pesel, height, weight, email);
        this.universityName = universityName;
        this.studyYear = studyYear;
        this.studyField = studyField;
        this.scholarshipAmount = scholarshipAmount;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setStudyYear(LocalDate studyYear) {
        this.studyYear = studyYear;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public String getUniversityName() {
        return universityName;
    }

    public LocalDate getStudyYear() {
        return studyYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }
}

package com.example.personinfo.people.commands;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.pl.PESEL;


public class CreatePersonCommand implements IPersonCommand {

    private String type;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @PESEL
    private String pesel;
    @Positive
    @NotNull
    private Double height;
    @Positive
    @NotNull
    private Double weight;
    @Email
    private String email;

    public CreatePersonCommand() {
    }

    public CreatePersonCommand(String type, String firstName, String lastName, String pesel, Double height, Double weight, String email) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.height = height;
        this.weight = weight;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getEmail() {
        return email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

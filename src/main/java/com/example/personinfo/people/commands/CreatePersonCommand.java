package com.example.personinfo.people.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.pl.PESEL;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true,
        include = JsonTypeInfo.As.EXISTING_PROPERTY)
public abstract class CreatePersonCommand {

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

}

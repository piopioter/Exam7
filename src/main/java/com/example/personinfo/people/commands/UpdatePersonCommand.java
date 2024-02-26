package com.example.personinfo.people.commands;

import com.example.personinfo.people.config.commandsdeserializer.updatedeserializator.PersonUpdateCommandDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;

@JsonDeserialize(using = PersonUpdateCommandDeserializer.class)
public abstract class UpdatePersonCommand  {


    private String type;
    @NotNull
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    //@PESEL
    private String pesel;
    @Positive
    @NotNull
    private Double height;
    @Positive
    @NotNull
    private Double weight;
    //@Email
    private String email;
    private Long version;

    public UpdatePersonCommand() {
    }

    public UpdatePersonCommand(String type, Long id, String firstName, String lastName, String pesel,
                               Double height, Double weight, String email, Long version) {
        this.type = type;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return id;
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

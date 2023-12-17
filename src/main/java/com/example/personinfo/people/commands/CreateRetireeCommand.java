package com.example.personinfo.people.commands;

import com.example.personinfo.people.annotations.PersonType;
import com.example.personinfo.people.models.Retiree;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
@PersonType(Retiree.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRetireeCommand extends CreatePersonCommand {
    @Positive
    @NotNull
    private BigDecimal pensionAmount;
    @Positive
    @NotNull
    private Integer yearsWorked;

    public CreateRetireeCommand() {
    }

    public CreateRetireeCommand(String type, String firstName, String lastName,
                                String pesel, Double height, Double weight, String email,
                                BigDecimal pensionAmount, Integer yearsWorked) {
        super(type, firstName, lastName, pesel, height, weight, email);
        this.pensionAmount = pensionAmount;
        this.yearsWorked = yearsWorked;
    }

    public BigDecimal getPensionAmount() {
        return pensionAmount;
    }

    public Integer getYearsWorked() {
        return yearsWorked;
    }
}

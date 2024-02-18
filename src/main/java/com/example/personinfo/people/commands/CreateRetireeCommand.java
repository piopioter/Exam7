package com.example.personinfo.people.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
@JsonDeserialize
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

package com.example.personinfo.people.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
@JsonDeserialize
public class UpdateRetireeCommand  extends UpdatePersonCommand {
    @Positive
    @NotNull
    private BigDecimal pensionAmount;
    @Positive
    @NotNull
    private Integer yearsWorked;

    public UpdateRetireeCommand() {

    }

    public UpdateRetireeCommand(String type, Long id, String firstName, String lastName, String pesel, Double height,
                                Double weight, String email, Long version, BigDecimal pensionAmount, Integer yearsWorked) {
        super(type, id, firstName, lastName, pesel, height, weight, email, version);
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

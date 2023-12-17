package com.example.personinfo.people.dto;

import java.math.BigDecimal;

public class RetireeDto extends PersonDto{

    private BigDecimal pensionAmount;
    private Integer yearsWorked;

    public BigDecimal getPensionAmount() {
        return pensionAmount;
    }

    public void setPensionAmount(BigDecimal pensionAmount) {
        this.pensionAmount = pensionAmount;
    }

    public Integer getYearsWorked() {
        return yearsWorked;
    }

    public void setYearsWorked(Integer yearsWorked) {
        this.yearsWorked = yearsWorked;
    }
}

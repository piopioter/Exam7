package com.example.personinfo.people.models;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@DiscriminatorValue("Retiree")
public class Retiree extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal pensionAmount;
    private Integer yearsWorked;

    public Retiree() {
    }

    public Retiree(String firstName, String lastName, String pesel,
                   Double height, Double weight, String email, BigDecimal pensionAmount, Integer yearsWorked) {
        super(firstName, lastName, pesel, height, weight, email);
        this.pensionAmount = pensionAmount;
        this.yearsWorked = yearsWorked;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Retiree retiree = (Retiree) o;
        return Objects.equals(pensionAmount, retiree.pensionAmount) && Objects.equals(yearsWorked, retiree.yearsWorked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pensionAmount, yearsWorked);
    }
}

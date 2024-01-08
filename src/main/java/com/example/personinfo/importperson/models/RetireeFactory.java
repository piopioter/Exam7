package com.example.personinfo.importperson.models;

import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Retiree;

import java.math.BigDecimal;

public class RetireeFactory implements PersonFactory{
    @Override
    public Person createPerson(String[] parts) {
        return new Retiree(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                Double.valueOf(parts[5]), parts[6], BigDecimal.valueOf(Double.parseDouble(parts[7])),
                Integer.valueOf(parts[8]));
    }
}

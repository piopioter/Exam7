package com.example.personinfo.importperson.models;

import com.example.personinfo.people.models.Person;
import com.example.personinfo.people.models.Retiree;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service("retiree")
public class RetireeFactory implements PersonFactory{

    @Override
    public Person createPerson(String[] parts) {
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Retiree(parts[1], parts[2], parts[3], Double.valueOf(parts[4]),
                Double.valueOf(parts[5]), parts[6], BigDecimal.valueOf(Double.parseDouble(parts[7])),
                Integer.valueOf(parts[8]));
    }


}

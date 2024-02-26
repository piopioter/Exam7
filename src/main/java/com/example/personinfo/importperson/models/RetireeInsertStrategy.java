package com.example.personinfo.importperson.models;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("retiree")
public class RetireeInsertStrategy implements PersonInsertStrategy {

    @Override
    public void insert(JdbcTemplate  jdbcTemplate, String[] parts) {
        jdbcTemplate.update("INSERT INTO person (type, email, first_name, height, last_name, " +
                        "pesel, weight, pension_amount, years_worked) VALUES (?,?,?,?,?,?,?,?,?)",
                parts[0], parts[6], parts[1], parts[4], (parts[2]), (parts[3]), parts[5], parts[7], (parts[8]));
    }


}

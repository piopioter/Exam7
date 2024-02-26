package com.example.personinfo.importperson.models;


import org.springframework.jdbc.core.JdbcTemplate;

public interface PersonInsertStrategy {

    void insert(JdbcTemplate jdbcTemplate, String[] parts);


}


package com.example.personinfo.importperson.dto;

import java.time.LocalDateTime;

public class StatusDto {

    private String message;
    private String id;

    public StatusDto(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}

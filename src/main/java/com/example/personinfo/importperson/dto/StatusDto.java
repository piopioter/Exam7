package com.example.personinfo.importperson.dto;

public class StatusDto {

    private String id;
    private String fileName;

    public StatusDto(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

}

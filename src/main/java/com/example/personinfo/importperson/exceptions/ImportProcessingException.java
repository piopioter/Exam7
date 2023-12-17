package com.example.personinfo.importperson.exceptions;

public class ImportProcessingException extends RuntimeException{

    public ImportProcessingException(String message) {
        super(message);
    }

    public ImportProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

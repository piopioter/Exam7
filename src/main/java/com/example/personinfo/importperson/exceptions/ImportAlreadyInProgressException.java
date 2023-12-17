package com.example.personinfo.importperson.exceptions;

public class ImportAlreadyInProgressException extends RuntimeException{
    public ImportAlreadyInProgressException(String message) {
        super(message);
    }

    public ImportAlreadyInProgressException(String message, Throwable cause) {
        super(message, cause);
    }
}

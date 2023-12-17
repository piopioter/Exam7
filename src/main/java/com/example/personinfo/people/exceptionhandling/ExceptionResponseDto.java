package com.example.personinfo.people.exceptionhandling;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponseDto {
    private List<String> message;
    private String statusError;
    private LocalDateTime localDateTime;

    public ExceptionResponseDto(List<String> message, String statusError, LocalDateTime localDateTime) {
        this.message = message;
        this.statusError = statusError;
        this.localDateTime = localDateTime;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getStatusError() {
        return statusError;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}

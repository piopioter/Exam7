package com.example.personinfo.people.exceptionhandling;

import com.example.personinfo.importperson.exceptions.ImportAlreadyInProgressException;
import com.example.personinfo.importperson.exceptions.ImportProcessingException;
import com.example.personinfo.people.exceptions.DataConflictException;
import com.example.personinfo.people.exceptions.ResourceNotFoundException;
import com.example.personinfo.people.exceptions.UnsupportedCommandException;
import com.example.personinfo.position.exceptions.InvalidDateRangeException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()), "NOT_FOUND", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ExceptionResponseDto> handleDataConflictException(DataConflictException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()), "CONFLICT", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }

    @ExceptionHandler(ImportProcessingException.class)
    public ResponseEntity<ExceptionResponseDto> handleImportProcessingException(ImportProcessingException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()), "BAD_REQUEST", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    @ExceptionHandler(ImportAlreadyInProgressException.class)
    public ResponseEntity<ExceptionResponseDto> handleImportAlreadyInProgressException(ImportAlreadyInProgressException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()), "CONFLICT", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }

    @ExceptionHandler({InvalidDateRangeException.class, UnsupportedCommandException.class})
    public ResponseEntity<ExceptionResponseDto> handleInvalidDateRangeException(RuntimeException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()), "BAD_REQUEST", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                List.of(e.getMessage()),
                "BAD_REQUEST",
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                getErrorsMessages(e),
                "BAD_REQUEST",
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    private List<String> getErrorsMessages(MethodArgumentNotValidException e) {
        return e.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

    }
}

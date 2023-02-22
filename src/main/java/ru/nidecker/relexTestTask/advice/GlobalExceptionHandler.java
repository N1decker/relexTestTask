package ru.nidecker.relexTestTask.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.nidecker.relexTestTask.exception.FieldAlreadyTakenException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FieldAlreadyTakenException.class})
    public ResponseEntity<?> handleException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

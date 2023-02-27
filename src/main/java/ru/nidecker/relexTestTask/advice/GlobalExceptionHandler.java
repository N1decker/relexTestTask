package ru.nidecker.relexTestTask.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.exception.FieldAlreadyTakenException;
import ru.nidecker.relexTestTask.exception.NotValidFieldException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //TODO: SHOULD RETURN JSON

    @ExceptionHandler({FieldAlreadyTakenException.class, ConstraintViolationException.class, EntityAlreadyExistsException.class, NotFoundException.class, IllegalArgumentException.class, NotValidFieldException.class})
    public ResponseEntity<?> badRequestHandleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DontHaveEnoughRightsException.class})
    public ResponseEntity<?> forbiddenHandleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}

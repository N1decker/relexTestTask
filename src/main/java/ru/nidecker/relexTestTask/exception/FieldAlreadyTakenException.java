package ru.nidecker.relexTestTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldAlreadyTakenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FieldAlreadyTakenException(String message) {
        super(message);
    }
}

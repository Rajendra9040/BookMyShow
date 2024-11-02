package com.scaler.bookmyshow.advice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class ProgramException extends RuntimeException {
    private final HttpStatus status;
    public ProgramException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public ProgramException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public ProgramException(String message, Throwable e) {
        super(message, e);
        this.status = HttpStatus.BAD_REQUEST;
    }
}

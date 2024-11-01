package com.scaler.bookmyshow.advice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ProgramException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public ProgramException() {this("Something went wrong!");}
    public ProgramException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public ProgramException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


}

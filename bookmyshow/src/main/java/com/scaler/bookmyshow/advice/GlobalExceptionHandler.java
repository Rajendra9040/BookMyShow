package com.scaler.bookmyshow.advice;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import com.scaler.bookmyshow.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ProgramException.class})
    public ResponseEntity<CommonResponse> handleProgramException(ProgramException exception) {
        CommonResponse response = new CommonResponse().setMessage(exception.getMessage())
            .setStatus(exception.getStatus());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException exception) {
        CommonResponse response = new CommonResponse().setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

package com.ptit.sign.advice;

import com.ptit.sign.dto.MappingResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GeneralExceptionalHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MappingResponse handleRuntimeException(NotFoundException e) {
        return new MappingResponse("error", null, e.getMessage());
    }
}

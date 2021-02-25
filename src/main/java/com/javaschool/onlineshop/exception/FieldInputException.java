package com.javaschool.onlineshop.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FieldInputException extends RuntimeException {

    private final BindingResult bindingResult;

    private final Map<String, String> errorsMap;

    public FieldInputException(BindingResult bindingResult, String message) {
        super(message);
        this.bindingResult = bindingResult;
        this.errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
    }

    Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
            fieldError -> fieldError.getField() + "Error",
            FieldError::getDefaultMessage
    );

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public Map<String, String> getErrorsMap() {
        return errorsMap;
    }
}


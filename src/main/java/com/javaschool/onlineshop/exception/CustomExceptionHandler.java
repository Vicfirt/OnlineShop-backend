package com.javaschool.onlineshop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Component
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(FieldInputException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(FieldInputException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(exception.getErrorsMap());

    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<EmailExistsException> handleEmailExistsError(EmailExistsException exception) {
        LOGGER.error(exception.getCustomerEmailAddressError(), exception);
        return ResponseEntity.badRequest().body(exception);
    }

    @ExceptionHandler(LoginRequestException.class)
    public ResponseEntity<String> handleLoginRequestException(LoginRequestException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}

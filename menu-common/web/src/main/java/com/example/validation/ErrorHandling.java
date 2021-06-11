package com.example.validation;


import com.example.exception.CustomerNotFoundException;
import com.example.exception.MenuNotFoundException;
import com.example.exception.RestaurantNotFoundException;
import com.example.validation.exception.ExceptionAnswer;
import com.example.validation.violation.ValidationErrorResponse;
import com.example.validation.violation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(value = { MenuNotFoundException.class,
            RestaurantNotFoundException.class,
            CustomerNotFoundException.class,
            IllegalArgumentException.class })
    public ResponseEntity onNotFoundException(Exception e) {
        ExceptionAnswer exceptionAnswer = new ExceptionAnswer();

        exceptionAnswer.setType(e.getClass().toString());
        exceptionAnswer.setMessage(e.getMessage());

        return new ResponseEntity(exceptionAnswer, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        constraintViolations.forEach(v -> {
            Violation responseViolation = new Violation();
            responseViolation.setFieldName(v.getPropertyPath().toString());
            responseViolation.setMessage(v.getMessage());

            error.getViolations().add(responseViolation);
        });

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach(f -> {
            Violation responseViolation = new Violation();
            responseViolation.setFieldName(f.getField());
            responseViolation.setMessage(f.getDefaultMessage());

            error.getViolations().add(responseViolation);
        });

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}

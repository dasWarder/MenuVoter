package com.example.validation;


import com.example.exception.CustomerNotFoundException;
import com.example.exception.MenuNotFoundException;
import com.example.exception.RestaurantNotFoundException;
import com.example.validation.exception.ExceptionResponse;
import com.example.validation.violation.ValidationResponse;
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

    @ExceptionHandler(value = {
                                MenuNotFoundException.class,
                                RestaurantNotFoundException.class,
                                CustomerNotFoundException.class })
    public ResponseEntity onNotFoundException(Exception exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setType(exception
                                           .getClass()
                                           .toString());
        exceptionResponse.setMessage(exception
                                             .getMessage());

        return new ResponseEntity(exceptionResponse,
                                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
                               ConstraintViolationException.class })
    public ResponseEntity onConstraintValidationException(ConstraintViolationException constraintViolationException) {

        ValidationResponse error = new ValidationResponse();

        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException
                                                                                       .getConstraintViolations();

        constraintViolations.forEach(violation -> {

                Violation responseViolation = new Violation();

                responseViolation.setFieldName(violation
                                                        .getPropertyPath()
                                                        .toString());
                responseViolation.setMessage(violation
                                                      .getMessage());

                error
                     .getViolations()
                     .add(responseViolation);
        });

        return new ResponseEntity(error,
                                  HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {
                               MethodArgumentNotValidException.class })
    public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        ValidationResponse error = new ValidationResponse();

        List<FieldError> fieldErrors = methodArgumentNotValidException
                                                                       .getBindingResult()
                                                                       .getFieldErrors();

        fieldErrors.forEach(fieldError -> {

                Violation responseViolation = new Violation();

                responseViolation.setFieldName(fieldError
                                                        .getField());
                responseViolation.setMessage(fieldError
                                                       .getDefaultMessage());

                error
                     .getViolations()
                     .add(responseViolation);
        });

        return new ResponseEntity(error,
                                  HttpStatus.BAD_REQUEST);
    }
}

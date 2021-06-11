package com.example.validation.exception;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionAnswer {

    private String type;

    private String message;
}

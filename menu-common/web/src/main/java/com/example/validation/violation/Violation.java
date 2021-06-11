package com.example.validation.violation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Violation {

    private String fieldName;

    private String message;
}

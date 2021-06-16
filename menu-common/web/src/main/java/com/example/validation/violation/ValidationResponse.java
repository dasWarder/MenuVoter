package com.example.validation.violation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationResponse {

    private List<Violation> violations = new ArrayList<>();
}

package com.example.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VoteDto implements Serializable {

    @NotNull(message =
            "The menu ID must be not null")
    private String menuId;


    @Email(message =
            "The email must be valid")
    @NotBlank(message =
            "The email is mandatory")
    private String email;


    @NotNull(message =
            "The rate must be not null")
    @Min(value = 1, message =
            "The rate can't be less that 1")
    @Max(value = 10, message =
            "The rate can't be greater that 10")
    private Double rate;
}

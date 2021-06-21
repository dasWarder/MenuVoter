package com.example.dto;

import com.example.menu.Dish;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MenuDto {

    private String id;


    @NotNull(message =
            "Creating date must be not Null")
    @JsonFormat(pattern = "yyyy-MM-dd",
                shape = JsonFormat.Shape.STRING)
    private LocalDate creatingDate;


    @Valid
    @Size(min = 1)
    @NotEmpty(message =
            "The dishes list must be not empty")
    private List<Dish> dishes;


    public MenuDto(LocalDate creatingDate, List<Dish> dishes) {
        this.creatingDate = creatingDate;
        this.dishes = dishes;
    }
}

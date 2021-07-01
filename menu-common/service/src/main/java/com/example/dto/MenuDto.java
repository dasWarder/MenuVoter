package com.example.dto;

import com.example.menu.Dish;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MenuDto implements Serializable {

    private String id;


    @Valid
    @Size(min = 1)
    @NotEmpty(message =
            "The dishes list must be not empty")
    private List<Dish> dishes;


    public MenuDto( List<Dish> dishes) {
        this.dishes = dishes;
    }
}

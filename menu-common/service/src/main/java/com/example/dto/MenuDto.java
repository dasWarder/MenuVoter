package com.example.dto;

import com.example.menu.Dish;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd",
            shape = JsonFormat.Shape.STRING)
    private LocalDate creatingDate;

    private List<Dish> dishes;
}

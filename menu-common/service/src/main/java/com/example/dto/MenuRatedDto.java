package com.example.dto;

import com.example.menu.Dish;
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
public class MenuRatedDto {

    private String id;

    private Double rate;

    private Long votes;

    private LocalDate creatingDate;

    private List<Dish> dishes;
}

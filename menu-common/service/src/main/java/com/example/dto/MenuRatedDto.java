package com.example.dto;

import com.example.Dish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRatedDto {

    private String id;

    private Double rate;

    private LocalDateTime creatingDate;

    private List<Dish> dishes;
}

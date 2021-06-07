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
public class MenuDto {

    private String id;

    private LocalDateTime creatingDate;

    private List<Dish> dishes;
}

package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Menu {
    private String id;
    private Float rate;
    private LocalDateTime dateOfOpenMenu;
    private List<Dish> dishes;
}

package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu")
public class Menu {

    @Id
    private String id;

    @Field(value = "restaurant_id")
    private Long restaurantId;

    @Field(value = "rate")
    private Double rate;

    @Field(value = "creating_date")
    private LocalDateTime creatingDate;

    @Field(value = "dishes")
    private List<Dish> dishes;
}
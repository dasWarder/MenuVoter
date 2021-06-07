package com.example;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu")
public class Menu {

    @Id
    private String id;

    @Indexed
    @Field(value = "restaurant_id")
    private Long restaurantId;

    @Field(value = "rate")
    private Double rate;

    @Field(value = "creating_date")
    private LocalDate creatingDate;

    @Field(value = "dishes")
    private List<Dish> dishes;
}

package com.example.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    @Field(value = "votes")
    private Long votes;

    @Field(value = "creating_date")
    @JsonFormat(pattern = "yyyy-MM-dd",
            shape = JsonFormat.Shape.STRING)
    private LocalDate creatingDate;

    @Field(value = "dishes")
    private List<Dish> dishes;

    public Menu(Long restaurantId, Double rate, Long votes, LocalDate creatingDate, List<Dish> dishes) {
        this.restaurantId = restaurantId;
        this.rate = rate;
        this.votes = votes;
        this.creatingDate = creatingDate;
        this.dishes = dishes;
    }
}

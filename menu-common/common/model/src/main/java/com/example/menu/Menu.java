package com.example.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu")
public class Menu implements Serializable {

    @Id
    private String id;

    @Indexed
    @Field(value = "restaurant_id")
    @NotNull(message = "The restaurant ID must be not null")
    private Long restaurantId;

    @Field(value = "rate")
    @NotNull(message = "The rate field must be not null")
    @Min(value = 1, message = "The rate can't be less that 1")
    @Max(value = 10, message = "The rate can't be greater than 10")
    private Double rate;

    @Field(value = "votes")
    @JsonProperty(value = "votes")
    @NotNull(message = "The votes field must be not null")
    @Min(value = 0, message = "The votes can't be less than 0")
    private Long votesCount;

    @Field(value = "creating_date")
    @JsonFormat(pattern = "yyyy-MM-dd",
            shape = JsonFormat.Shape.STRING)
    @NotNull(message = "The creating date can't be equals null")
    private LocalDate creatingDate;

    @Valid
    @Field(value = "dishes")
    @NotNull(message = "The dishes list must be not null")
    private List<Dish> dishes;

    public Menu(Long restaurantId, Double rate, Long votesCount, LocalDate creatingDate, List<Dish> dishes) {
        this.restaurantId = restaurantId;
        this.rate = rate;
        this.votesCount = votesCount;
        this.creatingDate = creatingDate;
        this.dishes = dishes;
    }
}

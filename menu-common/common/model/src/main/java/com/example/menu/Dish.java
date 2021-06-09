package com.example.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dish {

    @JsonProperty(value = "title", index = 1)
    @Field(value = "name")
    private String name;

    @Field(value = "description")
    private String description;
}

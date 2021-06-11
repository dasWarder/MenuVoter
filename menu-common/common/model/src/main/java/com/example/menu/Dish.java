package com.example.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dish {

    @Field(value = "name")
    @JsonProperty(value = "title", index = 1)
    @NotBlank(message = "The title is mandatory")
    @Size(min = 1, max = 60, message = "The size of title must be between 1 and 60")
    private String name;

    @Field(value = "description")
    @NotBlank(message = "The description is mandatory")
    @Size(min = 5, max = 255, message = "The size of description must be between 5 and 255")
    private String description;
}

package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Field(value = "name")
    private String name;

    @Field(value = "description")
    private String description;
}

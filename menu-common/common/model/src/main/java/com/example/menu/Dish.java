package com.example.menu;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Field(value = "name")
    private String name;

    @Field(value = "description")
    private String description;
}

package com.example.restaurant;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @JsonProperty(index = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    @JsonProperty(value = "title", index = 2)
    @NotBlank(message = "The title is mandatory")
    @Size(min = 1, max = 60, message = "The size of title must be between 1 and 60")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "The description is mandatory")
    @Size(min = 10, max = 255, message = "The description size must be  between 10 and 255")
    private String description;

    public Restaurant(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dish")
public class Dish extends AbstractIdEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
}

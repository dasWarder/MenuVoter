package com.example.model;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends AbstractIdEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}

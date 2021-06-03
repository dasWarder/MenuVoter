package com.example.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
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

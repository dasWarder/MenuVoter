package com.example.model;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
public class Menu extends AbstractIdEntity {

    @Column(name = "rate")
    private Float rate;

    @Column(name = "creating_date")
    private LocalDateTime dateOfOpenMenu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "menus_dishes",
            joinColumns = { @JoinColumn(name = "menu_id") },
            inverseJoinColumns = { @JoinColumn(name = "dish_id") }
    )
    private Set<Dish> dishes;
}

package com.example.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "voted")
    private boolean voted;

}

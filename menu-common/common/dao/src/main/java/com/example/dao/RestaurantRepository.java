package com.example.dao;

import com.example.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}

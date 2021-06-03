package com.example.service.restaurant;

import com.example.dao.RestaurantRepository;
import com.example.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        return (List<Restaurant>) restaurantRepository.findAll();
    }
}

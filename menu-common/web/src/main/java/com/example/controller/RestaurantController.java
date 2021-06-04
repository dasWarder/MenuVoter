package com.example.controller;



import com.example.Restaurant;
import com.example.restaurant.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/restaurants")
    public ResponseEntity getAll() {

        log.info("Receiving a collection of restaurants into the restaurant controller");
        List<Restaurant> restaurants = restaurantService.getAll();

        return new ResponseEntity(restaurants, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurants/restaurant")
    public ResponseEntity save(@RequestBody Restaurant restaurant) {

        log.info("Storing a new restaurant with the name = {}", restaurant.getName());
        Restaurant storedRestaurant = restaurantService.save(restaurant);

        return new ResponseEntity(storedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping(value = "/restaurants/restaurant/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long restaurantId) {

        log.info("Receiving a restaurant with ID = ", restaurantId);
        Restaurant receivedRestaurant = restaurantService.getById(restaurantId);

        return new ResponseEntity(receivedRestaurant, HttpStatus.OK);
    }

    @PutMapping(value = "/restaurants/restaurant/{id}")
    public ResponseEntity update(@PathVariable("id") Long restaurantId,
                                 @RequestBody Restaurant restaurant) {

        log.info("Updating a restaurant with ID = ", restaurantId);
        Restaurant storedRestaurant = restaurantService.update(restaurantId, restaurant);

        return new ResponseEntity(storedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/restaurant/{id}")
    public ResponseEntity delete(@PathVariable("id") Long restaurantId) {

        log.info("Removing a restaurant with ID = ", restaurantId);
        restaurantService.delete(restaurantId);

        String response = String.format("The restaurant with ID = %d was successfully removed", restaurantId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}

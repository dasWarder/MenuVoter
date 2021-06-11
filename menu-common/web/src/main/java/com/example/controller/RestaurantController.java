package com.example.controller;



import com.example.restaurant.Restaurant;
import com.example.service.restaurant.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {

        log.info("Receiving a collection of restaurants into the restaurant controller");
        List<Restaurant> restaurants = restaurantService.getAll();

        return new ResponseEntity(restaurants, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurant")
    public ResponseEntity<Restaurant> save(@RequestBody @Valid Restaurant restaurant) {

        log.info("Storing a new restaurant with the name = {}", restaurant.getName());
        Restaurant storedRestaurant = restaurantService.save(restaurant);

        return new ResponseEntity(storedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping(value = "/restaurant/{id}")
    public ResponseEntity<Restaurant> getOne(@PathVariable("id")
                                                 @Min(value = 1, message = "The Id must be greater that 0") Long restaurantId) {

        log.info("Receiving a restaurant with ID = ", restaurantId);
        Restaurant receivedRestaurant = restaurantService.getById(restaurantId);

        return new ResponseEntity(receivedRestaurant, HttpStatus.OK);
    }

    @PutMapping(value = "/restaurant/{id}")
    public ResponseEntity<Restaurant> update(@PathVariable("id")
                                                 @Min(value = 1, message = "The Id must be greater that 0") Long restaurantId,
                                 @RequestBody @Valid Restaurant restaurant) {

        log.info("Updating a restaurant with ID = ", restaurantId);
        Restaurant storedRestaurant = restaurantService.update(restaurantId, restaurant);

        return new ResponseEntity(storedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurant/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id")
                                             @Min(value = 1, message = "The Id must be greater that 0") Long restaurantId) {

        log.info("Removing a restaurant with ID = ", restaurantId);
        restaurantService.delete(restaurantId);

        String response = String.format("The restaurant with ID = %d was successfully removed", restaurantId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurant")
    public ResponseEntity<Restaurant> getByName(@RequestParam("name")
                                                    @Size(min = 1, max = 60, message = "The name must be between 1 and 60") String name) {

        log.info("Receiving a restaurant by its name = {}", name);
        Restaurant restaurantByName = restaurantService.getByName(name);

        return new ResponseEntity<>(restaurantByName, HttpStatus.OK);
    }

}

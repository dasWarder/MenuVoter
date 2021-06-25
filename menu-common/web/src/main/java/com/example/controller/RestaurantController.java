package com.example.controller;


import com.example.exception.EntityNotFoundException;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping( "/restaurants")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {

        log.info("Receiving a collection of restaurants into the restaurant controller");

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        return new ResponseEntity(restaurants,
                                  HttpStatus.OK);
    }


    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody
                                                     @Valid
                                                     Restaurant restaurant) {

        log.info("Storing a new restaurant with the name = {}",
                                                               restaurant.getName());
        Restaurant storedRestaurant = restaurantService.saveRestaurant(restaurant);

        return new ResponseEntity(storedRestaurant,
                                   HttpStatus.CREATED);
    }


    @GetMapping( "/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id")
                                                        @Min(value = 1, message =
                                                                "The Id must be greater that 0")
                                                        @NotNull(message =
                                                                "The Id of a restaurant must be not null")
                                                        Long restaurantId) throws EntityNotFoundException {

        log.info("Receiving a restaurant with ID = {}",
                                                    restaurantId);
        Restaurant receivedRestaurant = restaurantService.getRestaurantById(restaurantId);

        return new ResponseEntity(receivedRestaurant,
                                        HttpStatus.OK);
    }


    @PutMapping( "/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody
                                                       @Valid
                                                       Restaurant restaurant,
                                                       @PathVariable("id")
                                                       @Min(value = 1, message =
                                                               "The Id must be greater that 0")
                                                       @NotNull(message =
                                                               "The Id of a restaurant must be not null")
                                                       Long restaurantId) {

        log.info("Updating a restaurant with ID = {}",
                                                    restaurantId);
        Restaurant storedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurant);

        return new ResponseEntity(storedRestaurant,
                                        HttpStatus.OK);
    }


    @DeleteMapping( "/restaurant/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable(value = "id")
                                                   @Min(value = 1, message =
                                                           "The Id must be greater that 0")
                                                   @NotNull(message =
                                                           "The Id of a restaurant must be not null")
                                                   Long restaurantId) {

        log.info("Removing a restaurant with ID = {}",
                                                    restaurantId);
        restaurantService.deleteRestaurantById(restaurantId);
        String response = String.format(
                                        "The restaurant with ID = %d was successfully removed",
                                                                                              restaurantId);

        return new ResponseEntity(response,
                                  HttpStatus.OK);
    }


    @GetMapping( "/restaurant")
    public ResponseEntity<Restaurant> getRestaurantByName(@RequestParam("name")
                                                          @Size(min = 1, max = 60, message =
                                                                  "The name must be between 1 and 60")
                                                          @NotBlank(message =
                                                                  "The name must be not empty")
                                                          String name) throws EntityNotFoundException {

        log.info("Receiving a restaurant by its name = {}",
                                                           name);
        Restaurant restaurantByName = restaurantService.getRestaurantByName(name);

        return new ResponseEntity<>(restaurantByName,
                                          HttpStatus.OK);
    }

}

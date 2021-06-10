package com.example.service.restaurant;



import com.example.RestaurantRepository;
import com.example.exception.RestaurantNotFoundException;
import com.example.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.example.service.TestData.*;


@Slf4j
class RestaurantServiceTest {

    private RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);

    private RestaurantService restaurantService = new RestaurantServiceImpl(restaurantRepository);


    @Test
    public void shouldReturnAllRestaurantsProperly() {
        log.info("Test correctness of receiving a list of all restaurants");
        Mockito.when(restaurantRepository.findAll()).thenReturn(RESTAURANTS);

        List<Restaurant> serviceRestaurants = restaurantService.getAll();

        Assertions.assertEquals(RESTAURANTS, serviceRestaurants);
    }

    @Test
    public void shouldSaveRestaurantProperly() {
        log.info("Test correctness of saving a restaurant");
        Restaurant restaurant = new Restaurant("BuritoBar", "The Burito Bar");
        Mockito.when(restaurantRepository.save(restaurant)).thenReturn(TEST_RESTAURANT_3);

        Restaurant storedRestaurant = restaurantService.save(restaurant);

        Assertions.assertEquals(TEST_RESTAURANT_3, storedRestaurant);
    }

    @Test
    public void shouldThrowExceptionWhenSaveWrongRestaurant() {
        log.info("Test throwing IllegalArgumentException when saving NULL");
        Restaurant restaurant = null;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.save(restaurant));
    }


    @Test
    public void shouldGetRestaurantByIdProperly() {
        log.info("Test correctness of receiving a restaurant by ID");
        Long restaurantId = 1L;
        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(TEST_RESTAURANT));

        Restaurant possibleRestaurant = restaurantService.getById(restaurantId);

        Assertions.assertEquals(TEST_RESTAURANT, possibleRestaurant);
    }

    @Test
    public void shouldThrowExceptionWhenGetByNullId() {
        log.info("Test throwing IllegalArgumentException when getById = NULL");
        Long restaurantId = null;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.getById(restaurantId));
    }

    @Test
    public void shouldThrowExceptionWhenGetByIdWrong() {
        log.info("Test throwing RestaurantNotFoundException when getById with a wrong ID");
        Long restaurantId = WRONG_ID;
        Restaurant restaurant = null;
        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.ofNullable(restaurant));

        Assertions.assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getById(restaurantId));
    }


    @Test
    public void shouldGetRestaurantByNameProperly() {
        log.info("Test correctness of receiving a restaurant by name");
        String name = TEST_RESTAURANT_2.getName();
        Mockito.when(restaurantRepository.getRestaurantByName(name)).thenReturn(Optional.of(TEST_RESTAURANT_2));

        Restaurant restaurant = restaurantService.getByName(name);

        Assertions.assertEquals(TEST_RESTAURANT_2, restaurant);
    }

    @Test
    public void shouldThrowExceptionWhenGetByNullName() {
        log.info("Test throwing IllegalArgumentException when a name = NULL");
        String name = null;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.getByName(name));
    }

    @Test
    public void shouldThrowExceptionWhenGetWrongName() {
        log.info("Test throwing RestaurantNotFoundException when a restaurant with a wrong name");
        String name = WRONG_NAME;
        Mockito.when(restaurantRepository.getRestaurantByName(name)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getByName(name));
    }

    @Test
    public void shouldUpdateRestaurantProperly() {
        log.info("Test correctness of updating restaurant");
        Mockito.when(restaurantRepository.save(UPDATED_RESTAURANT)).thenReturn(UPDATED_RESTAURANT);
        Long restaurantId = TEST_RESTAURANT.getId();
        Restaurant updating = TEST_UPDATING_RESTAURANT;
        updating.setId(restaurantId);

        Restaurant updatedRestaurant = restaurantService.update(restaurantId, updating);

        Assertions.assertEquals(UPDATED_RESTAURANT, updatedRestaurant);
    }

    @Test
    public void shouldThrowExceptionWhenWhenUpdateRestaurantNullId() {
        log.info("Test throwing IllegalArgumentException when restaurantId is NULL");
        Mockito.when(restaurantRepository.save(TEST_UPDATING_RESTAURANT)).thenReturn(TEST_UPDATING_RESTAURANT);
        Long restaurantId = null;
        Restaurant updating = TEST_UPDATING_RESTAURANT;
        updating.setId(restaurantId);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(restaurantId, updating));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateRestaurantIsNull() {
        log.info("Test throwing IllegalArgumentException when a restaurant is NULL");
        Long restaurantId = TEST_RESTAURANT.getId();
        Restaurant updating = null;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.update(restaurantId, updating));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteRestaurantNullId() {
        log.info("Test throwing IllegalArgumentException when a restaurant ID is NULL");
        Long restaurantId = null;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> restaurantService.delete(restaurantId));
    }



}
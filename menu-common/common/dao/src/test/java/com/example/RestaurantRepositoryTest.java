package com.example;

import com.example.config.RepoConfig;
import com.example.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.TestData.TEST_RESTAURANT;


@Slf4j
@SpringBootTest(classes = { RepoConfig.class })
class RestaurantRepositoryTest {

    private RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);

    @BeforeEach
    public void init() {
        Mockito.when(restaurantRepository.getRestaurantByName("Wasabi"))
                .thenReturn(Optional.of(TEST_RESTAURANT));
    }

    @Test
    public void shouldReturnRestaurantByNameCorrectly() {
        log.info("Test correctness of getByName method");
        Optional<Restaurant> wasabi = restaurantRepository.getRestaurantByName("Wasabi");

        Restaurant restaurant = wasabi.get();

        Assertions.assertEquals(TEST_RESTAURANT, restaurant);
    }

}
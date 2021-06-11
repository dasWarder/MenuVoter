package com.example;

import com.example.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface MenuRepository extends MongoRepository<Menu, String> {

    List<Menu> getAllByRestaurantId(long restaurantId);

    Optional<Menu> getMenuByIdAndRestaurantId(String menuId, long restaurantId);

    void deleteMenuByIdAndRestaurantId(String menuId, long restaurantId);

    Optional<Menu> getMenuByCreatingDateAndRestaurantId(LocalDate creatingDate, long restaurantId);
}

package com.example;

import com.example.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface MenuRepository extends MongoRepository<Menu, String> {

    List<Menu> getMenusByRestaurantId(long restaurantId);

    Optional<Menu> getMenuByIdAndRestaurantId(String menuId, long restaurantId);

    @Transactional
    void deleteMenuByIdAndRestaurantId(String menuId, long restaurantId);

    Optional<Menu> getMenuByCreatingDateAndRestaurantId(LocalDate creatingDate, long restaurantId);
}

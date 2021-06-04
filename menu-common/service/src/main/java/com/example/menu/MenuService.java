package com.example.menu;

import com.example.Menu;

import java.time.LocalDateTime;
import java.util.List;

public interface MenuService {

    List<Menu> getAllByRestaurantId(long restaurantId);

    Menu save(Menu menu, long restaurantId);

    Menu getById(String menuId, long restaurantId);

    void deleteById(String menuId, long restaurantId);

    Menu getByCreatingDate(LocalDateTime creatingDate);

    Menu update(long restaurantId, String menuId, Menu menu);
}

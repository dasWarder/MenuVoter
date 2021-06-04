package com.example.menu;

import com.example.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAllByRestaurantId(long restaurantId);
    Menu save(Menu menu);
}

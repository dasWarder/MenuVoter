package com.example.controller;


import com.example.Menu;
import com.example.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/restaurants/{id}")
    public List<Menu> getAllByRestaurant(@PathVariable("id") long restaurantId) {
        return menuService.getAllByRestaurantId(restaurantId);
    }

    @PostMapping(value = "/restaurants/menu")
    public ResponseEntity<Menu> saveMenu(@RequestBody Menu menu) {
        Menu stored = menuService.save(menu);

        return new ResponseEntity<>(stored, HttpStatus.CREATED);
    }
}

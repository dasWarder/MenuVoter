package com.example.controller;

import com.example.model.Menu;
import com.example.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/menus/{id}")
    public List<Menu> getAll(@PathVariable("id") long id) {
        return menuService.getMenuByRestaurant_Id(id);
    }
}

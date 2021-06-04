package com.example.controller;


import com.example.Menu;
import com.example.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/restaurants/restaurant/{restId}/menus")
    public ResponseEntity getAllByRestaurant(@PathVariable("restId") long restaurantId) {

        log.info("Receiving a collection of menus for a restaurant with ID = ", restaurantId);
        List<Menu> menus = menuService.getAllByRestaurantId(restaurantId);

        return new ResponseEntity(menus, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurants/restaurant/{restId}/menu")
    public ResponseEntity<Menu> save(@RequestBody Menu menu,
                                         @PathVariable("restId") Long restaurantId) {

        log.info("Storing the menu with ID = {} for a restaurant with ID = ",
                menu.getId(), menu.getRestaurantId());
        Menu stored = menuService.save(menu, restaurantId);

        return new ResponseEntity<>(stored, HttpStatus.CREATED);
    }

    @GetMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity getOne(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Receiving the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        Menu receivedMenu = menuService.getById(menuId, restaurantId);

        return new ResponseEntity(receivedMenu, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity delete(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Removing a menu for ID = {} and the restaurant ID = {}", menuId, restaurantId);
        menuService.deleteById(menuId, restaurantId);

        String response = String
                .format("The menu with ID = %s and a restaurant ID = %d was successfully removed",
                        menuId, restaurantId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity update(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId,
                                 @RequestBody Menu menu) {

        log.info("Updating the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        Menu updatedMenu = menuService.update(restaurantId, menuId, menu);

        return new ResponseEntity(updatedMenu, HttpStatus.OK);
    }
}

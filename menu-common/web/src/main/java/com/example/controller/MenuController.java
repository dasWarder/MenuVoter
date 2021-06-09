package com.example.controller;


import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.service.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/restaurants/restaurant/{restId}/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuRatedDto>> getAllByRestaurant(@PathVariable("restId") long restaurantId) {

        log.info("Receiving a collection of menus for a restaurant with ID = {}", restaurantId);
        List<MenuRatedDto> menus = menuService.getAllByRestaurantId(restaurantId);

        return new ResponseEntity(menus, HttpStatus.OK);
    }

    @PostMapping(value = "/menu")
    public ResponseEntity<MenuRatedDto> save(@RequestBody MenuDto menuDto,
                                         @PathVariable("restId") Long restaurantId) {

        log.info("Storing the menu from = {} for a restaurant with ID = {}",
                menuDto.getCreatingDate(), restaurantId);

        MenuRatedDto stored = menuService.save(menuDto, restaurantId);

        return new ResponseEntity<>(stored, HttpStatus.CREATED);
    }

    @GetMapping(value = "/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> getOne(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Receiving the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        MenuRatedDto receivedMenu = menuService.getById(menuId, restaurantId);

        return new ResponseEntity(receivedMenu, HttpStatus.OK);
    }

    @DeleteMapping(value = "/menu/{menuId}")
    public ResponseEntity<String> delete(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Removing a menu for ID = {} and the restaurant ID = {}", menuId, restaurantId);
        menuService.deleteById(menuId, restaurantId);

        String response = String
                .format("The menu with ID = %s and a restaurant ID = %d was successfully removed",
                        menuId, restaurantId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping(value = "/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> update(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId,
                                 @RequestBody MenuDto menuDto) {

        log.info("Updating the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        MenuRatedDto updatedMenu = menuService.update(restaurantId, menuId, menuDto);

        return new ResponseEntity(updatedMenu, HttpStatus.OK);
    }

    @GetMapping(value = "/menu")
    public ResponseEntity<MenuRatedDto> getOnDateMenu(@PathVariable("restId") Long restaurantId,
                                                      @RequestParam(value = "date", required = false)
                                                      @DateTimeFormat(iso =
                                                              DateTimeFormat.ISO.DATE) LocalDate menuDate) {

        log.info("Get the menu by the date of creating menu for the restaurant with ID = {}", restaurantId);
        MenuRatedDto menu = menuService.getByCreatingDate(menuDate, restaurantId);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

}

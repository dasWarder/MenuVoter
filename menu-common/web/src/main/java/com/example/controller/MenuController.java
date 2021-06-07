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
import java.time.LocalDateTime;
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
    public ResponseEntity<List<MenuRatedDto>> getAllByRestaurant(@PathVariable("restId") long restaurantId) {

        log.info("Receiving a collection of menus for a restaurant with ID = {}", restaurantId);
        List<MenuRatedDto> menus = menuService.getAllByRestaurantId(restaurantId);

        return new ResponseEntity(menus, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurants/restaurant/{restId}/menu")
    public ResponseEntity<MenuRatedDto> save(@RequestBody MenuDto menuDto,
                                         @PathVariable("restId") Long restaurantId) {

        log.info("Storing the menu from = {} for a restaurant with ID = {}",
                menuDto.getCreatingDate(), restaurantId);

        MenuRatedDto stored = menuService.save(menuDto, restaurantId);

        return new ResponseEntity<>(stored, HttpStatus.CREATED);
    }

    @GetMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> getOne(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Receiving the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        MenuRatedDto receivedMenu = menuService.getById(menuId, restaurantId);

        return new ResponseEntity(receivedMenu, HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity<String> delete(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId) {

        log.info("Removing a menu for ID = {} and the restaurant ID = {}", menuId, restaurantId);
        menuService.deleteById(menuId, restaurantId);

        String response = String
                .format("The menu with ID = %s and a restaurant ID = %d was successfully removed",
                        menuId, restaurantId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping(value = "/restaurants/restaurant/{restId}/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> update(@PathVariable("restId") Long restaurantId,
                                 @PathVariable("menuId") String menuId,
                                 @RequestBody MenuDto menuDto) {

        log.info("Updating the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
        MenuRatedDto updatedMenu = menuService.update(restaurantId, menuId, menuDto);

        return new ResponseEntity(updatedMenu, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/restaurant/{restId}/menu")
    public ResponseEntity<MenuRatedDto> getOnDateMenu(@PathVariable("restId") Long restaurantId,
                                                      @RequestParam(value = "date")
                                                      @DateTimeFormat(iso =
                                                              DateTimeFormat.ISO.DATE_TIME) LocalDateTime menuDate) {

        log.info("Get the menu by the date of creating menu for the restaurant with ID = {}", restaurantId);
        MenuRatedDto menu = menuService.getByCreatingDate(menuDate);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}

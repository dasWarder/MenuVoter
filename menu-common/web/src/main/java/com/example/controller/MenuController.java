package com.example.controller;


import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.exception.EntityNotFoundException;
import com.example.service.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/restaurants/restaurant/{restId}/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @GetMapping
    public ResponseEntity<List<MenuRatedDto>> getAllMenusByRestaurantId(@PathVariable("restId")
                                                                        @Min(value = 1, message =
                                                                                "The Id must be greater that 0")
                                                                        @NotNull(message =
                                                                                "The Id of a restaurant must be not null")
                                                                        Long restaurantId) {

        log.info("Receiving a collection of menus for a restaurant with ID = {}",
                                                                                restaurantId);
        List<MenuRatedDto> menus = menuService.getAllMenusByRestaurantId(restaurantId);

        return new ResponseEntity(menus,
                                  HttpStatus.OK);
    }


    @PostMapping( "/menu")
    public ResponseEntity<MenuRatedDto> saveMenu(@RequestBody
                                                 @Valid
                                                 MenuDto menuDto,
                                                 @PathVariable("restId")
                                                 @Min(value = 1,message =
                                                         "The Id must be greater that 0")
                                                 @NotNull(message =
                                                         "The Id of a restaurant must be not null")
                                                 Long restaurantId) {

        log.info("Storing the menu from = {} for a restaurant with ID = {}",
                                                                            menuDto.getCreatingDate(),
                                                                            restaurantId);
        MenuRatedDto stored = menuService.saveMenu(menuDto, restaurantId);

        return new ResponseEntity<>(stored,
                                    HttpStatus.CREATED);
    }


    @GetMapping( "/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> getMenuById(@PathVariable("menuId")
                                                    @NotBlank
                                                    String menuId,
                                                    @PathVariable("restId")
                                                    @Min(value = 1, message =
                                                            "The Id must be greater that 0")
                                                    @NotNull(message =
                                                            "The Id of a restaurant must be not null")
                                                    Long restaurantId) throws EntityNotFoundException {

        log.info("Receiving the menu with ID = {} and a restaurant ID = {}",
                                                                            menuId,
                                                                            restaurantId);
        MenuRatedDto receivedMenu = menuService.getMenuById(menuId, restaurantId);

        return new ResponseEntity(receivedMenu,
                                  HttpStatus.OK);
    }


    @DeleteMapping( "/menu/{menuId}")
    public ResponseEntity<String> deleteMenuById(@PathVariable("menuId")
                                                 @NotBlank
                                                 String menuId,
                                                 @PathVariable("restId")
                                                 @Min(value = 1, message =
                                                         "The Id must be greater that 0")
                                                 @NotNull(message =
                                                         "The Id of a restaurant must be not null")
                                                 Long restaurantId) {

        log.info("Removing a menu for ID = {} and the restaurant ID = {}",
                                                                          menuId,
                                                                          restaurantId);
        menuService.deleteMenuById(menuId, restaurantId);
        String response = String.format(
                            "The menu with ID = %s and a restaurant ID = %d was successfully removed",
                                                                                                      menuId,
                                                                                                      restaurantId);
        return new ResponseEntity(response,
                                  HttpStatus.OK);
    }


    @PutMapping( "/menu/{menuId}")
    public ResponseEntity<MenuRatedDto> updateMenu(@RequestBody
                                                   @Valid
                                                   MenuDto menuDto,
                                                   @PathVariable("menuId")
                                                   @NotBlank String menuId,
                                                   @PathVariable("restId")
                                                   @Min(value = 1, message =
                                                           "The Id must be greater that 0")
                                                   @NotNull(message =
                                                           "The Id of a restaurant must be not null")
                                                   Long restaurantId) throws EntityNotFoundException {

        log.info("Updating the menu with ID = {} and a restaurant ID = {}",
                                                                           menuId,
                                                                           restaurantId);
        MenuRatedDto updatedMenu = menuService.updateMenu(restaurantId, menuId, menuDto);

        return new ResponseEntity(updatedMenu,
                                   HttpStatus.OK);
    }


    @GetMapping("/menu")
    public ResponseEntity<MenuRatedDto> getMenuByDateOrDefaultTodayMenu(@RequestParam(value = "date", required = false)
                                                                        @DateTimeFormat(iso =
                                                                                DateTimeFormat.ISO.DATE)
                                                                        LocalDate menuDate,
                                                                        @PathVariable("restId")
                                                                        @Min(value = 1, message =
                                                                                "The Id must be greater that 0")
                                                                        @NotNull(message =
                                                                                "The Id of a restaurant must be not null")
                                                                        Long restaurantId) throws EntityNotFoundException {

        log.info("Get the menu by the date of creating menu for the restaurant with ID = {}",
                                                                                             restaurantId);
        MenuRatedDto menu = menuService.getMenuByCreatingDate(menuDate, restaurantId);

        return new ResponseEntity<>(menu,
                                    HttpStatus.OK);
    }

}

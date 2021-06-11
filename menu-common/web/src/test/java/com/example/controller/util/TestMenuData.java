package com.example.controller.util;

import com.example.dto.MenuDto;
import com.example.menu.Menu;
import com.example.validation.exception.ExceptionAnswer;

import java.time.LocalDate;
import java.util.List;

import static com.example.controller.util.TestData.*;

public class TestMenuData {

    //Menu test data for testing controller
    public static final Menu FIRST_RESTAURANT_FIRST_MENU = new Menu(
            "60c1a31acb80bdb5e24c44cc", 1L, 0.0, 0L,
            LocalDate.of(2021, 06, 9),
            List.of(TEST_DISH));

    public static final Menu FIRST_RESTAURANT_SECOND_MENU = new Menu(
            "60c1a31acb80bdb5e24c44cd", 1L, 0.0, 0L,
            LocalDate.now(),
            List.of(TEST_DISH_2, TEST_DISH_3, TEST_DISH_4));

    //DTO to test CREATE and UPDATE methods
    public static final MenuDto SECOND_RESTAURANT_CREATE_DTO = new MenuDto(
            "60c1ec6082f1f45502f687c0",
            LocalDate.of(2021, 12, 1),
            List.of(TEST_DISH_5, TEST_DISH_9, TEST_DISH_7));

    public static final MenuDto FIRST_RESTAURANT_UPDATE_DTO = new MenuDto(
            FIRST_RESTAURANT_FIRST_MENU.getId(),
            LocalDate.of(2021, 06, 9),
            List.of(TEST_DISH_5, TEST_DISH_6, TEST_DISH_7));

    //a message as a body to ResponseEntity when DELETE
    public static final String REMOVE_MESSAGE = "The menu with ID = %s and a restaurant ID = %d was successfully removed";

    //Test validation from the service layer
    public static final ExceptionAnswer MENU_NOT_FOUND = new ExceptionAnswer(
            "class com.example.exception.MenuNotFoundException",
            String.format("The menu with ID = %s not founded", WRONG_ID_STRING));

}

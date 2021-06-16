package com.example.service.mapping;

import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.service.TestData.*;

@Slf4j
class MappingServiceTest {

    private MappingService mappingService = new MappingServiceImpl();

    @Test
    public void shouldMapFromDtoToMenuProperly() {
        log.info("Test correctness of mapping from MenuDTO to Menu");

        Menu menu = mappingService.mappingFromDtoToMenu(TEST_MENU_DTO, TEST_RESTAURANT.getId());

        Assertions.assertNotNull(menu);
        Assertions.assertEquals(TEST_MENU, menu);
    }

    @Test
    public void shouldThrowExceptionWhenDTOIsNull() {
        log.info("Test throwing IllegalArgumentException when DTO is NULL");
        MenuDto menuDto = null;
        Long restaurantId = TEST_RESTAURANT.getId();

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromDtoToMenu(menuDto, restaurantId));
    }

    @Test
    public void shouldThrowExceptionWhenRestaurantIdIsNull() {
        log.info("Test throwing IllegalArgumentException when a restaurant ID is NULL");
        Long restaurantId = null;
        MenuDto menuDto = TEST_MENU_DTO;

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromDtoToMenu(menuDto, restaurantId));
    }

    @Test
    public void shouldMapFromNullIdDtoToMenuProperly() {
        log.info("Test correctness of mapping from MenuDTO to Menu");
        Long restaurantId = TEST_RESTAURANT.getId();

        Menu menu = mappingService.mappingFromDtoToMenu(TEST_MENU_DTO_WITH_NULL_ID, restaurantId);

        Assertions.assertNotNull(menu);
        Assertions.assertEquals(TEST_MENU_WITH_NULL_ID, menu);
    }

    @Test
    public void shouldMapFromMenuToRatedDtoProperly() {
        log.info("Test correctness of mapping from Menu to RatedMenuDto");

        MenuRatedDto menuRatedDto = mappingService.mappingFromMenuToRatedDto(TEST_MENU_WITH_RATE);

        Assertions.assertNotNull(menuRatedDto);
        Assertions.assertEquals(TEST_RATED_DTO, menuRatedDto);
    }

    @Test
    public void shouldThrowExceptionWhenMenuIsNull() {
        log.info("Test throwing IllegalArgumentException when try to receive a RatedDTO with NULL menu");
        Menu menu = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromMenuToRatedDto(menu));
    }

    @Test
    public void shouldMapFromListOfMenuDtoToMenuListProperly() {
        log.info("Test correctness of mapping a list of MenuDTOs to a list of Menus");
        List<MenuDto> menuDtoList = List.of(TEST_MENU_DTO, TEST_MENU_DTO_WITH_NULL_ID);
        Long restaurantId = TEST_RESTAURANT.getId();
        List<Menu> expectedMenu = List.of(TEST_MENU, TEST_MENU_WITH_NULL_ID);

        List<Menu> actualMenus = mappingService.mappingFromMenuDtoListToMenuList(menuDtoList, restaurantId);

        Assertions.assertNotNull(actualMenus);
        Assertions.assertEquals(expectedMenu, actualMenus);
    }

    @Test
    public void shouldMapForEmptyMenuDtoListProperly() {
        log.info("Test correctness of mapping an empty list of MenuDTOs");
        List<MenuDto> emptyList = new ArrayList<>();
        Long restaurantId = TEST_RESTAURANT.getId();

        List<Menu> menus = mappingService.mappingFromMenuDtoListToMenuList(emptyList, restaurantId);

        Assertions.assertNotNull(menus);
        Assertions.assertTrue(menus.isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenMenuDtoListIsNull() {
        log.info("Test throwing IllegalArgumentException when try to map a NULL list of MenuDTO");
        List<MenuDto> nullableMenuDto = null;
        Long restaurantId = TEST_RESTAURANT.getId();

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromMenuDtoListToMenuList(nullableMenuDto, restaurantId));
    }

    @Test
    public void shouldThrowExceptionWhenListIsOkAndRestaurantIdIsNull() {
        log.info("Test throwing IllegalArgumentException when try to map with a NULL restaurant ID");
        List<MenuDto> menuDtoList = List.of(TEST_MENU_DTO, TEST_MENU_DTO_WITH_NULL_ID);
        Long restaurantId = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromMenuDtoListToMenuList(menuDtoList, restaurantId));
    }

    @Test
    public void shouldMapFromMenuListToRatedDtoListProperly() {
        log.info("Test correctness of mapping from a list of menus to a RatedDTO list");
        List<Menu> menus = List.of(TEST_MENU_WITH_RATE, TEST_MENU);
        List<MenuRatedDto> expectedList = List.of(TEST_RATED_DTO, TEST_RATED_DTO_2);

        List<MenuRatedDto> actualList = mappingService.mappingFromMenuListToMenuRatedDtoList(menus);

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList.size(), actualList.size());
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldMapEmptyMenuListProperly() {
        log.info("Test correctness of mapping an empty list of menus");
        List<Menu> menus = new ArrayList<>();

        List<MenuRatedDto> menuRatedDtoList = mappingService.mappingFromMenuListToMenuRatedDtoList(menus);

        Assertions.assertNotNull(menuRatedDtoList);
        Assertions.assertTrue(menuRatedDtoList.isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenMenuListIsNull() {
        log.info("Test throwing IllegalArgumentException when a Menu list is NULL");
        List<Menu> menus = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> mappingService.mappingFromMenuListToMenuRatedDtoList(menus));
    }
}
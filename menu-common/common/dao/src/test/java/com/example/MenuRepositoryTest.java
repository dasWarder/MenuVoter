package com.example;


import com.example.config.DaoConfig;
import com.example.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.TestData.*;

@Slf4j
@SpringBootTest(classes = { DaoConfig.class })
class MenuRepositoryTest {

    private final MenuRepository menuRepository = Mockito.mock(MenuRepository.class);

    @BeforeEach
    public void init() {
        Mockito.when(menuRepository.getMenuByIdAndRestaurantId("abcd12345", 1L))
                .thenReturn(Optional.of(TEST_MENU));

        Mockito.when(menuRepository.getMenuByCreatingDateAndRestaurantId(LocalDate.now(), 2L))
                .thenReturn(Optional.of(TEST_MENU_2));

        Mockito.when(menuRepository.getMenusByRestaurantId(1L))
                .thenReturn(FIRST_RESTAURANT_MENUS);
    }

    @Test
    public void shouldReturnMenuByIdAndRestaurantIdProperly() {
        log.info("Test correctness of receiving by ID and restaurant ID");
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId("abcd12345", 1L);
        Menu menu = possibleMenu.get();

        Assertions.assertEquals(TEST_MENU, menu);
    }

    @Test
    public void shouldReturnMenuByCreatingDateAndRestaurantIdProperly() {
        log.info("Test correctness of receiving by creating date and restaurant ID");
        Optional<Menu> possibleMenu = menuRepository.getMenuByCreatingDateAndRestaurantId(LocalDate.now(), 2L);

        Menu menu = possibleMenu.get();

        Assertions.assertEquals(TEST_MENU_2, menu);
    }

    @Test
    public void shouldReturnMenusByRestaurantIdProperly() {
        log.info("Test correctness of receiving a list of menus by a restaurant ID");
        List<Menu> menus = menuRepository.getMenusByRestaurantId(1L);

        Assertions.assertEquals(FIRST_RESTAURANT_MENUS, menus);
    }

}
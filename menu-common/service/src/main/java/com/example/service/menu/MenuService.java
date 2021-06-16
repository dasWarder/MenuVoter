package com.example.service.menu;

import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    List<MenuRatedDto> getAllMenusByRestaurantId(long restaurantId);

    MenuRatedDto saveMenu(MenuDto menuDtoToSave, long restaurantId);

    MenuRatedDto getMenuById(String menuId, long restaurantId);

    void deleteMenuById(String menuId, long restaurantId);

    MenuRatedDto getMenuByCreatingDate(LocalDate creatingDate, Long restaurantId);

    MenuRatedDto updateMenu(long restaurantId, String menuId, MenuDto menuDtoForUpdating);
}

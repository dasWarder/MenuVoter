package com.example.service.menu;

import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.exception.EntityNotFoundException;
import com.example.exception.MenuNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    List<MenuRatedDto> getAllMenusByRestaurantId(long restaurantId);

    MenuRatedDto saveMenu(MenuDto menuDtoToSave, long restaurantId);

    MenuRatedDto getMenuById(String menuId, long restaurantId) throws EntityNotFoundException;

    void deleteMenuById(String menuId, long restaurantId);

    MenuRatedDto getMenuByCreatingDate(LocalDate creatingDate, Long restaurantId) throws EntityNotFoundException;

    MenuRatedDto updateMenu(long restaurantId, String menuId, MenuDto menuDtoForUpdating) throws EntityNotFoundException;
}

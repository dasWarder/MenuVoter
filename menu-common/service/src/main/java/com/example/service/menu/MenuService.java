package com.example.service.menu;

import com.example.Menu;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    List<MenuRatedDto> getAllByRestaurantId(long restaurantId);

    MenuRatedDto save(MenuDto menuDto, long restaurantId);

    MenuRatedDto getById(String menuId, long restaurantId);

    void deleteById(String menuId, long restaurantId);

    MenuRatedDto getByCreatingDate(LocalDate creatingDate);

    MenuRatedDto update(long restaurantId, String menuId, MenuDto menuDto);
}

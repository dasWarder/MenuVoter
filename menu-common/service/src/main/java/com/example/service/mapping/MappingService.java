package com.example.service.mapping;

import com.example.menu.Menu;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;

import java.util.List;

public interface MappingService {

    Menu fromDtoToMenu(MenuDto dto, Long restaurantId);

    MenuRatedDto fromMenuToRatedDto(Menu menu);

    List<Menu> fromMenuDtoListToMenuList(List<MenuDto> listOfDto, Long restaurantId);

    List<MenuRatedDto> fromMenuListToMenuRatedDtoList(List<Menu> menuList);
}

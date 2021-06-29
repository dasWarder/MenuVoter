package com.example.service.mapping;

import com.example.menu.Menu;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;

import java.util.List;

public interface MappingService {

    Menu mappingFromDtoToMenu(MenuDto dto, Long restaurantId);

    MenuRatedDto mappingFromMenuToRatedDto(Menu menu);

    List<MenuRatedDto> mappingFromMenuListToMenuRatedDtoList(List<Menu> menuList);

    MenuRatedDto mappingFromMenuDtoToRatedMenuDTO(MenuDto dto, Long restaurantId);
}

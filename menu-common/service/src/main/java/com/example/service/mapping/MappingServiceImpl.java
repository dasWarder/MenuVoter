package com.example.service.mapping;

import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.ParamValidationUtil.validateParametersNotNull;


@Slf4j
@Service
public class MappingServiceImpl implements MappingService {

    @Override
    @Transactional
    public Menu mappingFromDtoToMenu(MenuDto dto, Long restaurantId) {

        validateParametersNotNull(dto, restaurantId);
        log.info("Mapping a new menu from menuDTO for the restaurant with ID = {}",
                                                                                   restaurantId);
        Menu newMenu = Menu.builder()
                                    .id(dto.getId())
                                    .restaurantId(restaurantId)
                                    .creatingDate(LocalDate.now())
                                    .dishes(dto.getDishes())
                                    .rate(0.0)
                                    .votesCount(0L)
                                    .build();
        return newMenu;
    }


    @Override
    @Transactional
    public MenuRatedDto mappingFromMenuToRatedDto(Menu menu) {

        validateParametersNotNull(menu);
        log.info("Mapping from menu to menuDTO an object with ID = {}",
                                                                       menu.getId());
        Long rate = Math.round(menu.getRate());

        MenuRatedDto dto = MenuRatedDto.builder()
                                                .id(menu.getId())
                                                .creatingDate(menu.getCreatingDate())
                                                .rate(rate.doubleValue())
                                                .votesCount(menu.getVotesCount())
                                                .dishes(menu.getDishes())
                                                .build();
        return dto;
    }


    @Override
    @Transactional
    public List<MenuRatedDto> mappingFromMenuListToMenuRatedDtoList(List<Menu> menuList) {

       validateParametersNotNull(menuList);
       log.info("Mapping from Menu List to Menu Rated DTO List");

       List<MenuRatedDto> menuRatedDtoList = new ArrayList<>(menuList.size());
       menuList.forEach(menu -> {

               Long rate = Math.round(menu.getRate());
               menuRatedDtoList.add(
                                    MenuRatedDto.builder()
                                                         .id(menu.getId())
                                                         .creatingDate(menu.getCreatingDate())
                                                         .dishes(menu.getDishes())
                                                         .rate(rate.doubleValue())
                                                         .votesCount(menu.getVotesCount())
                                                         .build());
       });

       return menuRatedDtoList;
    }


    @Override
    @Transactional
    public MenuRatedDto mappingFromMenuDtoToRatedMenuDTO(MenuDto dto, Long restaurantId) {

        validateParametersNotNull(dto, restaurantId);
        log.info("Mapping from MenuDto to RatedMenuDto");
        Menu menu = mappingFromDtoToMenu(dto,
                                             restaurantId);
        MenuRatedDto menuRatedDto = mappingFromMenuToRatedDto(menu);

        return menuRatedDto;
    }
}

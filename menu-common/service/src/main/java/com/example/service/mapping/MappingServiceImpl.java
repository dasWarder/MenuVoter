package com.example.service.mapping;

import com.example.Menu;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.service.rate.util.VoteCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;


@Slf4j
@Service
public class MappingServiceImpl implements MappingService {

    private VoteCounter voteCounter;

    @Autowired
    public MappingServiceImpl(VoteCounter voteCounter) {
        this.voteCounter = voteCounter;
    }

    @Override
    public Menu fromDtoToMenu(MenuDto dto, Long restaurantId) {
        notNull(dto, "The DTO object must be not NULL");
        notNull(restaurantId, "The restaurantId must be not NULL");

        log.info("Mapping a new menu from menuDTO for the restaurant with ID = {}", restaurantId);
        Menu newMenu = Menu.builder()
                .id(dto.getId())
                .restaurantId(restaurantId)
                .creatingDate(dto.getCreatingDate())
                .dishes(dto.getDishes())
                .rate(0.0)
                .build();

        if(dto.getId() != null) {
            log.info("Mapping DTO with ID = {}", dto.getId());
            newMenu.setId(dto.getId());
        }

        return newMenu;
    }

    @Override
    public MenuRatedDto fromMenuToRatedDto(Menu menu) {
        notNull(menu, "The menu object must be not NULL");

        log.info("Mapping from menu to menuDTO an object with ID = {}", menu.getId());
        long votes = voteCounter.getCurrentCount(menu.getId());

        MenuRatedDto dto = MenuRatedDto.builder()
                .id(menu.getId())
                .creatingDate(menu.getCreatingDate())
                .rate(menu.getRate())
                .votes(votes)
                .dishes(menu.getDishes())
                .build();

        return dto;
    }

    @Override
    public List<Menu> fromMenuDtoListToMenuList(List<MenuDto> listOfDto, Long restaurantId) {
        notNull(listOfDto, "The list of DTO must be not NULL");
        notNull(restaurantId, "The ID of a restaurant must be not NULL");

        List<Menu> menus = new ArrayList<>(listOfDto.size());

        log.info("Mapping from MenuDTO List to Menu List for the restaurant with ID = {}", restaurantId);
        listOfDto.forEach(dto -> {
            menus.add(
                    Menu.builder()
                            .id(dto.getId())
                            .creatingDate(dto.getCreatingDate())
                            .restaurantId(restaurantId)
                            .dishes(dto.getDishes())
                            .rate(0.0).build());
        });

        return menus;
    }

    @Override
    public List<MenuRatedDto> fromMenuListToMenuRatedDtoList(List<Menu> menuList) {
       notNull(menuList, "The list of menus must be not NULL");
       List<MenuRatedDto> menuRatedDtoList = new ArrayList<>(menuList.size());

       log.info("Mapping from Menu List to Menu Rated DTO List");
       menuList.forEach(menu -> {
           long votes = voteCounter.getCurrentCount(menu.getId());

           menuRatedDtoList.add(
                   MenuRatedDto.builder()
                           .id(menu.getId())
                           .creatingDate(menu.getCreatingDate())
                           .dishes(menu.getDishes())
                           .rate(menu.getRate())
                           .votes(votes).build());
       });

       return menuRatedDtoList;
    }
}

package com.example.service.menu;

import com.example.MenuRepository;
import com.example.Menu;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.exception.MenuNotFoundException;
import com.example.service.mapping.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.Assert.notNull;


@Slf4j
@Service
@EnableMongoRepositories(basePackageClasses = { MenuRepository.class })
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MappingService mappingService;

    public MenuServiceImpl(MenuRepository menuRepository, MappingService mappingService) {
        this.menuRepository = menuRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<MenuRatedDto> getAllByRestaurantId(long restaurantId) {
        notNull(restaurantId, "The ID of the restaurant must be NOT null");

        log.info("Receiving a collection of menus for the restaurant with ID = {}", restaurantId);
        List<Menu> menus = menuRepository.getAllByRestaurantId(restaurantId);

        log.info("Mapping from the list of Menu to List of Menu Rated Dto for the restaurant with ID = {}", restaurantId);
        List<MenuRatedDto> menuRatedDtoList = mappingService.fromMenuListToMenuRatedDtoList(menus);

        return menuRatedDtoList;
    }

    @Override
    public MenuRatedDto save(MenuDto menuDto, long restaurantId) {
        notNull(restaurantId, "The ID of a restaurant must be NOT null");

        log.info("Mapping from DTO to the menu for a restaurant with ID = {}", restaurantId);
        Menu menu = mappingService.fromDtoToMenu(menuDto, restaurantId);

        log.info("Storing the menu from date = {} for a restaurant with ID = {}", menuDto.getCreatingDate(), restaurantId);
        Menu storedMenu = menuRepository.save(menu);

        log.info("Mapped from the Menu to Rated DTO for the restaurant with ID = {} and a menu with ID ={}",
                restaurantId, menu.getId());
        MenuRatedDto menuRatedDtoWithId = mappingService.fromMenuToRatedDto(storedMenu);

        return menuRatedDtoWithId;
    }

    @Override
    public MenuRatedDto getById(String menuId, long restaurantId) {
        notNull(restaurantId, "The ID of a restaurant must be NOT null");
        notNull(menuId, "The ID of a menu must be NOT null");

        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {
            log.info("Receiving the menu by its ID = {}", menuId);
            Menu menu = possibleMenu.get();

            log.info("Mapping from the Menu to Rated DTO for a menu with ID = {}", menu.getId());
            MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(menu);

            return menuRatedDto;
        }

        log.info("The exception for menu with ID = {} has been occurred", menuId);
        throw new MenuNotFoundException(String
                .format("The menu with ID = %s not founded", menuId));
    }

    @Override
    public void deleteById(String menuId, long restaurantId) {
        notNull(menuId, "The ID for the menu must be NOT null");
        notNull(restaurantId, "The ID for the restaurant must be NOT null");

        log.info("Removing the menu with ID = {}", menuId);
        menuRepository.deleteMenuByIdAndRestaurantId(menuId, restaurantId);
    }

    @Override
    public MenuRatedDto getByCreatingDate(LocalDate creatingDate) {
        Optional<Menu> possibleMenu;

        if(creatingDate == null) {
            log.info("Receiving a menu for today");
            possibleMenu = menuRepository.getMenuByCreatingDate(LocalDate.now());
        } else {
            log.info("Receiving a menu for a creating date = {}", creatingDate);
            possibleMenu = menuRepository.getMenuByCreatingDate(creatingDate);
        }

        if(possibleMenu.isPresent()) {
            log.info("Receiving the menu with the date of creating = {}", creatingDate);
            Menu menu = possibleMenu.get();

            log.info("Mapping from the Menu to Rated DTO for a menu with ID = {}", menu.getId());
            MenuRatedDto RatedDto = mappingService.fromMenuToRatedDto(menu);

            return RatedDto;
        }

        log.info("The exception for creating date = {} has been occurred", creatingDate);
        throw new MenuNotFoundException(String
                .format("The menu with creating date = %s not founded", creatingDate));
    }

    @Override
    public MenuRatedDto update(long restaurantId, String menuId, MenuDto menuDto) {
        notNull(restaurantId, "The ID of the restaurant must be NOT null");
        notNull(menuId, "The ID for the menu must be NOT null");

        log.info("Mapping for UPDATE method for the restaurant with ID = {} and the menu with ID = {}", restaurantId, menuId);
        Menu menu = mappingService.fromDtoToMenu(menuDto, restaurantId);

        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {
            log.info("Updating the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);
            String menuFromDbId = possibleMenu.get()
                                                .getId();
            menu.setId(menuFromDbId);
            menu.setRestaurantId(restaurantId);

            Menu storedUpdatedMenu = menuRepository.save(menu);

            log.info("Mapping from the Menu to Rated DTO for a menu with ID = {}", menu.getId());
            MenuRatedDto menuRatedDtoUpdated = mappingService.fromMenuToRatedDto(storedUpdatedMenu);

            return menuRatedDtoUpdated;
        }

        log.info("The exception for updating the menu with ID = {} has been occurred", menuId);
        throw new MenuNotFoundException(String
                .format("The menu with ID = %s and the restaurant ID =  %d not founded", menuId, restaurantId));
    }
}

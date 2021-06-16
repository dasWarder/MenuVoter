package com.example.service.menu;

import com.example.MenuRepository;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.exception.MenuNotFoundException;
import com.example.menu.Menu;
import com.example.service.mapping.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;


@Slf4j
@Service
@EnableMongoRepositories(basePackageClasses = {
                                        MenuRepository.class })
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final MappingService mappingService;

    public MenuServiceImpl(MenuRepository menuRepository, MappingService mappingService) {
        this.menuRepository = menuRepository;
        this.mappingService = mappingService;
    }


    @Override
    public List<MenuRatedDto> getAllMenusByRestaurantId(long restaurantId) {

        notNull(restaurantId,
                     "The ID of the restaurant must be NOT null");
        log.info("Receiving a collection of menus for the restaurant with ID = {}",
                                                                                   restaurantId);
        List<Menu> menus = menuRepository.getMenusByRestaurantId(restaurantId);
        List<MenuRatedDto> menuRatedDtoList = mappingService.mappingFromMenuListToMenuRatedDtoList(menus);

        return menuRatedDtoList;
    }


    @Override
    @Transactional
    public MenuRatedDto saveMenu(MenuDto menuDtoToSave, long restaurantId) {

        notNull(restaurantId,
                     "The ID of a restaurant must be NOT null");
        Menu menu = mappingService.mappingFromDtoToMenu(menuDtoToSave, restaurantId);

        log.info("Storing the menu from date = {} for a restaurant with ID = {}",
                                                                                 menuDtoToSave.getCreatingDate(),
                                                                                 restaurantId);
        Menu storedMenu = menuRepository.save(menu);
        MenuRatedDto menuRatedDtoWithId = mappingService.mappingFromMenuToRatedDto(storedMenu);

        return menuRatedDtoWithId;
    }


    @Override
    public MenuRatedDto getMenuById(String menuId, long restaurantId) {

        notNull(restaurantId,
                     "The ID of a restaurant must be NOT null");
        notNull(menuId,
                "The ID of a menu must be NOT null");
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {

                    log.info("Receiving the menu by its ID = {}",
                                                                 menuId);
                    Menu menu = possibleMenu.get();
                    MenuRatedDto menuRatedDto = mappingService.mappingFromMenuToRatedDto(menu);

                    return menuRatedDto;
        }

        log.info("The exception for menu with ID = {} has been occurred",
                                                                         menuId);
        throw new MenuNotFoundException(String.format(
                                                        "The menu with ID = %s not founded",
                                                                                            menuId));
    }


    @Override
    @Transactional
    public void deleteMenuById(String menuId, long restaurantId) {

        notNull(menuId,
                "The ID for the menu must be NOT null");
        notNull(restaurantId,
                "The ID for the restaurant must be NOT null");
        log.info("Removing the menu with ID = {}",
                                                  menuId);
        menuRepository.deleteMenuByIdAndRestaurantId(menuId, restaurantId);
    }


    @Override
    public MenuRatedDto getMenuByCreatingDate(LocalDate creatingDate, Long restaurantId) {

        Optional<Menu> possibleMenu;

        if(creatingDate == null) {

            creatingDate = LocalDate.now();
            log.info("Receiving a menu for today {} and restaurant with ID = {}",
                                                                                creatingDate,
                                                                                restaurantId);
        } else {

            log.info("Receiving a menu for a creating date = {} and restaurant with ID = {}",
                                                                                            creatingDate,
                                                                                            restaurantId);
        }

        possibleMenu = menuRepository.getMenuByCreatingDateAndRestaurantId(creatingDate, restaurantId);

        if(possibleMenu.isPresent()) {

            Menu menu = possibleMenu.get();
            log.info("Mapping from the Menu to Rated DTO for a menu with ID = {}",
                                                                                  menu.getId());
            MenuRatedDto RatedDto = mappingService.mappingFromMenuToRatedDto(menu);

            return RatedDto;
        }

        log.info("The exception for creating date = {} has been occurred",
                                                                          creatingDate);
        throw new MenuNotFoundException(String.format(
                                                      "The menu with creating date = %s not founded",
                                                                                                    creatingDate));
    }


    @Override
    @Transactional
    public MenuRatedDto updateMenu(long restaurantId, String menuId, MenuDto menuDtoForUpdating) {

        notNull(restaurantId,
                     "The ID of the restaurant must be NOT null");
        notNull(menuId,
                "The ID for the menu must be NOT null");
        Menu menu = mappingService.mappingFromDtoToMenu(menuDtoForUpdating, restaurantId);
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {

            log.info("Updating the menu with ID = {} and a restaurant ID = {}",
                                                                                menuId,
                                                                                restaurantId);
            String IdOfMenuFromDb = possibleMenu.get().getId();
            menu.setId(
                       IdOfMenuFromDb);
            menu.setRestaurantId(
                                 restaurantId);
            Menu storedUpdatedMenu = menuRepository.save(menu);
            MenuRatedDto menuRatedDtoUpdated = mappingService.mappingFromMenuToRatedDto(storedUpdatedMenu);

            return menuRatedDtoUpdated;
        }

        log.info("The exception for updating the menu with ID = {} has been occurred",
                                                                                      menuId);
        throw new MenuNotFoundException(String.format(
                                                      "The menu with ID = %s and the restaurant ID =  %d not founded",
                                                                                                                menuId,
                                                                                                                restaurantId));
    }
}

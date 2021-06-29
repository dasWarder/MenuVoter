package com.example.service.menu;

import com.example.MenuRepository;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.exception.EntityNotFoundException;
import com.example.menu.Menu;
import com.example.service.mapping.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.util.OptionalValidation.checkOptionalAndReturnOrThrowException;
import static com.example.util.ParamValidationUtil.validateParametersNotNull;


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

        validateParametersNotNull(restaurantId);
        log.info("Receiving a collection of menus for the restaurant with ID = {}",
                                                                                   restaurantId);
        List<Menu> menus = menuRepository.getMenusByRestaurantId(restaurantId);
        List<MenuRatedDto> menuRatedDtoList = mappingService.mappingFromMenuListToMenuRatedDtoList(menus);

        return menuRatedDtoList;
    }


    @Override
    @Transactional
    public MenuRatedDto saveMenu(MenuDto menuDtoToSave, long restaurantId) {

        validateParametersNotNull(menuDtoToSave, restaurantId);
        Menu menu = mappingService.mappingFromDtoToMenu(menuDtoToSave, restaurantId);

        log.info("Storing today's menu for a restaurant with ID = {}",
                                                                                 restaurantId);
        Menu storedMenu = menuRepository.save(menu);

        return mapFromMenuToMenuRatedDto(storedMenu);
    }


    @Override
    public MenuRatedDto getMenuById(String menuId, long restaurantId) throws EntityNotFoundException {

        validateParametersNotNull(menuId, restaurantId);

        log.info("Receiving a menu with ID = {} for a restaurant with ID = {}",
                                                                                menuId,
                                                                                restaurantId);
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);
        Menu menuFromDB = checkOptionalAndReturnOrThrowException(possibleMenu, Menu.class);

        return mapFromMenuToMenuRatedDto(menuFromDB);
    }


    @Override
    @Transactional
    public void deleteMenuById(String menuId, long restaurantId) {

        validateParametersNotNull(menuId,restaurantId);
        log.info("Removing the menu with ID = {}",
                                                  menuId);
        menuRepository.deleteMenuByIdAndRestaurantId(menuId, restaurantId);
    }


    @Override
    public MenuRatedDto getMenuByCreatingDate(LocalDate creatingDate, Long restaurantId) throws EntityNotFoundException {

        validateParametersNotNull(restaurantId);
        creatingDate = validateCreatingDate(creatingDate);
        log.info("Receiving a menu by creating date = {} for a restaurant with ID = {}",
                                                                                        creatingDate,
                                                                                        restaurantId);
        Optional<Menu> possibleMenu = menuRepository.getMenuByCreatingDateAndRestaurantId(creatingDate, restaurantId);
        Menu menuFromDB = checkOptionalAndReturnOrThrowException(possibleMenu, Menu.class);

        return mapFromMenuToMenuRatedDto(menuFromDB);
    }


    @Override
    @Transactional
    public MenuRatedDto updateMenu(long restaurantId, String menuId, MenuDto menuDtoForUpdating) throws EntityNotFoundException {

        validateParametersNotNull(restaurantId, menuId, menuDtoForUpdating);
        Menu menu = mappingService.mappingFromDtoToMenu(menuDtoForUpdating, restaurantId);
        log.info("Updating menu with ID = {} for a restaurant with ID = {}",
                                                                            menuId,
                                                                            restaurantId);
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);
        Menu menuFromDB = checkOptionalAndReturnOrThrowException(possibleMenu, Menu.class);
        Menu storedAndUpdateMenu = updateMenuIntoDbAndReturn(menuFromDB, menu, restaurantId);

        return mapFromMenuToMenuRatedDto(storedAndUpdateMenu);
    }






    private MenuRatedDto mapFromMenuToMenuRatedDto(Menu menuToValidate) {

        MenuRatedDto menuRatedDtoUpdated = mappingService.mappingFromMenuToRatedDto(menuToValidate);

        return menuRatedDtoUpdated;
    }


    private LocalDate validateCreatingDate(LocalDate creatingDate) {

        if(creatingDate == null) {
            creatingDate = LocalDate.now();
        }

        return creatingDate;
    }


    private Menu updateMenuIntoDbAndReturn(Menu menuFromDB, Menu menuForUpdating, Long restaurantId) {

        String IdOfMenuFromDb = menuFromDB.getId();
        menuForUpdating.setId(
                             IdOfMenuFromDb);
        menuForUpdating.setRestaurantId(
                                        restaurantId);
        Menu storedUpdatedMenu = menuRepository.save(menuForUpdating);

        return storedUpdatedMenu;
    }
}

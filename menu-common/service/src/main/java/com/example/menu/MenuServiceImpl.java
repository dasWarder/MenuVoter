package com.example.menu;

import com.example.MenuRepository;
import com.example.Menu;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableMongoRepositories(basePackageClasses = { MenuRepository.class })
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getAllByRestaurantId(long restaurantId) {
        return menuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }
}

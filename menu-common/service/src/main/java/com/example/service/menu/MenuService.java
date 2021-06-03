package com.example.service.menu;

import com.example.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuByRestaurant_Id(long restId);
}

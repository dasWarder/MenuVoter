package com.example.dao;

import com.example.model.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {

    List<Menu> getMenuByRestaurant_Id(long restId);
}

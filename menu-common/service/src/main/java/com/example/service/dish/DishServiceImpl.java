package com.example.service.dish;

import com.example.dao.DishRepository;
import com.example.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    @Override
    public List<Dish> getAll() {
        return (List<Dish>) dishRepository.findAll();
    }
}

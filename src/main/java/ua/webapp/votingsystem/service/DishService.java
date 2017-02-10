package ua.webapp.votingsystem.service;


import ua.webapp.votingsystem.model.Dish;

import java.util.List;

public interface DishService {

    Dish save(Dish dish, int restaurantId);

    Dish get(int id, int restaurantId);

    List<Dish> getAll(int restaurantId);

    void delete(int id, int restaurantId);

    void update(Dish dish, int restaurantId);

    void evictCache();

}

package ua.webapp.votingsystem.repository;

import ua.webapp.votingsystem.model.Restaurant;

import java.util.List;



public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    List<Restaurant> getAllWithDishes();

}

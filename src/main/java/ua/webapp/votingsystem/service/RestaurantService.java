package ua.webapp.votingsystem.service;


import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.model.VotesAction;

import java.util.List;

public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    List<Restaurant> getAll();

    void update(Restaurant restaurant);

    void changeNumberOfVotes(int id, VotesAction action) throws NotFoundException;

    void evictCache();

    List<Restaurant> getAllWithDishes();
}

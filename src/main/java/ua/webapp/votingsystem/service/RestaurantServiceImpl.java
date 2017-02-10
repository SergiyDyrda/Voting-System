package ua.webapp.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.webapp.votingsystem.util.ExceptionUtil;
import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.repository.RestaurantRepository;
import ua.webapp.votingsystem.model.VotesAction;

import java.util.List;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        repository.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void changeNumberOfVotes(int id, VotesAction action) throws NotFoundException {
        Restaurant restaurant = get(id);
        Integer votes = restaurant.getVotes();
            if (action == VotesAction.INCREMENT)
                restaurant.setVotes(votes + 1);
            else if (action == VotesAction.DECREMENT)
                restaurant.setVotes(votes - 1);

            repository.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public void evictCache() {

    }

    @Override
    public List<Restaurant> getAllWithDishes() {
        return repository.getAllWithDishes();
    }

 }

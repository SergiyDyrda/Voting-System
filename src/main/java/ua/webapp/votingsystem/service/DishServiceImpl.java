package ua.webapp.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.webapp.votingsystem.util.ExceptionUtil;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.repository.DishRepository;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "dishes", allEntries = true)
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish);
        return ExceptionUtil.checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Override
    @Cacheable(value = "dishes")
    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    @Override
    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Override
    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish);
        ExceptionUtil.checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @Override
    @CacheEvict(value = "dishes", allEntries = true)
    public void evictCache() {
    }
}

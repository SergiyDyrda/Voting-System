package ua.webapp.votingsystem.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.service.DishService;

import java.util.List;

public abstract class AbstractDishController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService service;

    @Autowired
    public AbstractDishController(DishService service) {
        this.service = service;
    }

    public Dish create(Dish dish, Integer restaurantId) {
        log.info("create " + dish);
        return service.save(dish, restaurantId);
    }

    public void update(Dish dish, Integer restaurantId) {
        log.info("update " + dish);
        service.update(dish, restaurantId);
    }

    public void delete(Integer id, Integer restaurantId) {
        log.info("delete " + id);
        service.delete(id, restaurantId);
    }

    public Dish get(Integer id, Integer restaurantId) {
        log.info("get " + id);
        return service.get(id, restaurantId);
    }

    public List<Dish> getAll(Integer restaurantId) {
        log.info("getAll for restaurant" + restaurantId);
        return service.getAll(restaurantId);
    }
}

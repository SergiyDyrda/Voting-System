package ua.webapp.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.Dish;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ua.webapp.votingsystem.DishTestData.*;
import static ua.webapp.votingsystem.RestaurantTestData.ADMIN_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.RestaurantTestData.USERS_VOTED_RESTAURANT;


public class DishServiceImplTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void save() throws Exception {
        Dish newDish = new Dish("new Dish", "Very tasty", 236);
        Dish saved = service.save(newDish, ADMIN_VOTED_RESTAURANT.getId());
        newDish.setId(saved.getId());
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTED_RESTAURANT_DISH_1, ADMIN_VOTED_RESTAURANT_DISH_2, ADMIN_VOTED_RESTAURANT_DISH_3, newDish),
                service.getAll(ADMIN_VOTED_RESTAURANT.getId()));
    }

    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(USERS_VOTED_RESTAURANT_DISH_2,
                service.get(USERS_VOTED_RESTAURANT_DISH_2.getId(), USERS_VOTED_RESTAURANT.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherRestaurant() throws Exception {
        service.get(USERS_VOTED_RESTAURANT_DISH_2.getId(), ADMIN_VOTED_RESTAURANT.getId());
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTED_RESTAURANT_DISH_1,
                ADMIN_VOTED_RESTAURANT_DISH_2,
                ADMIN_VOTED_RESTAURANT_DISH_3),
                service.getAll(ADMIN_VOTED_RESTAURANT.getId()));

        MATCHER.assertCollectionsEquals(Arrays.asList(USERS_VOTED_RESTAURANT_DISH_1,
                USERS_VOTED_RESTAURANT_DISH_2,
                USERS_VOTED_RESTAURANT_DISH_3),
                service.getAll(USERS_VOTED_RESTAURANT.getId()));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USERS_VOTED_RESTAURANT_DISH_2.getId(), USERS_VOTED_RESTAURANT.getId());
        MATCHER.assertCollectionsEquals(Arrays.asList(USERS_VOTED_RESTAURANT_DISH_1,
                USERS_VOTED_RESTAURANT_DISH_3),
                service.getAll(USERS_VOTED_RESTAURANT.getId()));
    }

    @Test
    public void update() throws Exception {
        Dish dish = copyOf(USERS_VOTED_RESTAURANT_DISH_3);
        String newDishName = "New Dish name";
        dish.setName(newDishName);
        service.update(dish, USERS_VOTED_RESTAURANT.getId());
        assertEquals(newDishName, service.get(USERS_VOTED_RESTAURANT_DISH_3.getId(),
                USERS_VOTED_RESTAURANT.getId()).getName());
    }

    @Test(expected = NotFoundException.class)
    public void updateIncorrectRestaurant() throws Exception {
        Dish dish = copyOf(ADMIN_VOTED_RESTAURANT_DISH_3);
        String newDishName = "New Dish name";
        dish.setName(newDishName);
        service.save(dish, USERS_VOTED_RESTAURANT.getId());
    }

    @Test
    public void updateToAnotherRestaurant() throws Exception {
        Dish dish = copyOf(USERS_VOTED_RESTAURANT_DISH_3);
        dish.setRestaurant(ADMIN_VOTED_RESTAURANT);
        service.update(dish, USERS_VOTED_RESTAURANT.getId());
        MATCHER.assertEquals(USERS_VOTED_RESTAURANT_DISH_3, service.get(USERS_VOTED_RESTAURANT_DISH_3.getId(),
                USERS_VOTED_RESTAURANT.getId()));
    }
}
package ua.webapp.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.Restaurant;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static ua.webapp.votingsystem.DishTestData.USERS_VOTED_RESTAURANT_DISH_1;
import static ua.webapp.votingsystem.RestaurantTestData.*;


public class RestaurantServiceImplTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }


    @Test
    @Transactional
    public void save() throws Exception {
        Restaurant restaurant = new Restaurant(null, "New Restaurant",
                USERS_VOTED_RESTAURANT_DISH_1);
        Restaurant created = service.save(restaurant);
        restaurant.setId(created.getId());
        MATCHER.assertCollectionsEquals(
                Arrays.asList(ADMIN_VOTED_RESTAURANT, USERS_VOTED_RESTAURANT, restaurant),
                service.getAll());
    }

    @Test
    public void delete() throws Exception {
        service.delete(USERS_VOTED_RESTAURANT.getId());
        MATCHER.assertCollectionsEquals(
                Collections.singletonList(ADMIN_VOTED_RESTAURANT),
                service.getAllWithDishes());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(USERS_VOTED_RESTAURANT.getId() + 2);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = service.get(ADMIN_VOTED_RESTAURANT.getId());
        MATCHER.assertEquals(ADMIN_VOTED_RESTAURANT, actual);
        System.out.println(actual.getDishes().size());
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(5);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionsEquals(
                Arrays.asList(ADMIN_VOTED_RESTAURANT, USERS_VOTED_RESTAURANT),
                service.getAllWithDishes());

    }

    @Test
    public void update() throws Exception {
        String newName = "New restaurant name";
        Restaurant updated = copyOf(ADMIN_VOTED_RESTAURANT);
        updated.setName(newName);
        service.update(updated);
        assertEquals(newName, service.get(ADMIN_VOTED_RESTAURANT.getId()).getName());
    }

}
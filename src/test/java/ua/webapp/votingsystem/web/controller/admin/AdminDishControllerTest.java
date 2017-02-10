package ua.webapp.votingsystem.web.controller.admin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.webapp.votingsystem.DishTestData;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.service.DishService;
import ua.webapp.votingsystem.web.AbstractControllerTest;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.DishTestData.*;
import static ua.webapp.votingsystem.RestaurantTestData.ADMIN_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.RestaurantTestData.USERS_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.ADMIN;
import static ua.webapp.votingsystem.web.controller.admin.AdminRestaurantController.ADMIN_RESTAURANT_URL;


public class AdminDishControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void createDish() throws Exception {
        Dish created = DishTestData.copyOf(DishTestData.USERS_VOTED_RESTAURANT_DISH_3);
        created.setId(null);
        created.setName("New dish for admin restaurant");

        Dish returned = JsonUtil.readValue(mockMvc.perform(post(ADMIN_RESTAURANT_URL + '/' + ADMIN_VOTED_RESTAURANT.getId() + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(authenticate(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), Dish.class);

        created.setId(returned.getId());

        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTED_RESTAURANT_DISH_1,
                ADMIN_VOTED_RESTAURANT_DISH_2,
                ADMIN_VOTED_RESTAURANT_DISH_3,
                created), service.getAll(ADMIN_VOTED_RESTAURANT.getId()));
    }


    @Test
    public void updateRestaurant() throws Exception {
        Dish updated = DishTestData.copyOf(DishTestData.USERS_VOTED_RESTAURANT_DISH_2);
        updated.setName("New name for dish");

        mockMvc.perform(put(ADMIN_RESTAURANT_URL + '/' + USERS_VOTED_RESTAURANT.getId() + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MATCHER.contentMatcher(updated));

        MATCHER.assertEquals(updated, service.get(USERS_VOTED_RESTAURANT_DISH_2.getId(), USERS_VOTED_RESTAURANT.getId()));
    }

    @Test
    public void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(ADMIN_RESTAURANT_URL + '/' + USERS_VOTED_RESTAURANT.getId() + "/dishes/" + USERS_VOTED_RESTAURANT_DISH_1.getId())
                .with(authenticate(ADMIN)))
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionsEquals(Arrays.asList(USERS_VOTED_RESTAURANT_DISH_2, USERS_VOTED_RESTAURANT_DISH_3), service.getAll(USERS_VOTED_RESTAURANT.getId()));
    }

}
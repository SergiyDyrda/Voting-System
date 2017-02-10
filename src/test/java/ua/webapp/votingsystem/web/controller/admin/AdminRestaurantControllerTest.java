package ua.webapp.votingsystem.web.controller.admin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import ua.webapp.votingsystem.DishTestData;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.service.RestaurantService;
import ua.webapp.votingsystem.web.AbstractControllerTest;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.RestaurantTestData.*;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.ADMIN;
import static ua.webapp.votingsystem.web.controller.admin.AdminRestaurantController.ADMIN_RESTAURANT_URL;


public class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New Restaurant", DishTestData.copyOf(DishTestData.USERS_VOTED_RESTAURANT_DISH_1), DishTestData.copyOf(DishTestData.USERS_VOTED_RESTAURANT_DISH_2));
        Restaurant createdRestaurant = JsonUtil.readValue(mockMvc.perform(post(ADMIN_RESTAURANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(authenticate(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), Restaurant.class);

        newRestaurant.setId(createdRestaurant.getId());

        MATCHER.assertEquals(newRestaurant, createdRestaurant);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTED_RESTAURANT, USERS_VOTED_RESTAURANT, newRestaurant), service.getAll());

    }

    @Test
    public void updateRestaurant() throws Exception {
        Restaurant updatedRestaurant = copyOf(ADMIN_VOTED_RESTAURANT);
        String newName = "new restaurant name";
        updatedRestaurant.setName(newName);

        mockMvc.perform(put(ADMIN_RESTAURANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant))
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(updatedRestaurant));

        assertEquals(service.get(updatedRestaurant.getId()).getName(), newName);
    }

    @Test
    @Transactional
    public void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(ADMIN_RESTAURANT_URL + "/" + USERS_VOTED_RESTAURANT.getId())
                .with(authenticate(ADMIN)))
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTED_RESTAURANT), service.getAll());
    }

}
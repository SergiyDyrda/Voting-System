package ua.webapp.votingsystem.web.controller.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.webapp.votingsystem.service.DishService;
import ua.webapp.votingsystem.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.DishTestData.*;
import static ua.webapp.votingsystem.RestaurantTestData.USERS_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.USER_1;
import static ua.webapp.votingsystem.web.controller.user.UserRestaurantController.USER_RESTAURANT_URL;


public class DishControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void getDish() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + '/' + USERS_VOTED_RESTAURANT.getId() + "/dishes/" + USERS_VOTED_RESTAURANT_DISH_1.getId())
                .with(authenticate(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USERS_VOTED_RESTAURANT_DISH_1));
    }

    @Test
    public void getAllDish() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + '/' + USERS_VOTED_RESTAURANT.getId() + "/dishes")
                .with(authenticate(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(Arrays.asList(USERS_VOTED_RESTAURANT_DISH_1, USERS_VOTED_RESTAURANT_DISH_2, USERS_VOTED_RESTAURANT_DISH_3)));
    }

}
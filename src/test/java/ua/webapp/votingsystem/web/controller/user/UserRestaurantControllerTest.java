package ua.webapp.votingsystem.web.controller.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.webapp.votingsystem.RestaurantTestData;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.service.RestaurantService;
import ua.webapp.votingsystem.web.AbstractControllerTest;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.RestaurantTestData.ADMIN_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.RestaurantTestData.USERS_VOTED_RESTAURANT;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.*;
import static ua.webapp.votingsystem.web.controller.user.UserRestaurantController.USER_RESTAURANT_URL;


public class UserRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void getRestaurantUser1() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + "/" + USERS_VOTED_RESTAURANT.getId())
                .with(authenticate(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.MATCHER.contentMatcher(USERS_VOTED_RESTAURANT));
    }

    @Test
    public void getRestaurantUser3() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + "/" + ADMIN_VOTED_RESTAURANT.getId())
                .with(authenticate(USER_3)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.MATCHER.contentMatcher(ADMIN_VOTED_RESTAURANT));
    }

    @Test
    public void getRestaurantAdmin() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + "/" + USERS_VOTED_RESTAURANT.getId())
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.MATCHER.contentMatcher(USERS_VOTED_RESTAURANT));
    }

    @Test
    public void getRestaurantNotFound() throws Exception {
        mockMvc.perform(get(USER_RESTAURANT_URL + "/" + 3)
                .with(authenticate(USER_2)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getAllRestaurants() throws Exception {
        List<Restaurant> restaurants = JsonUtil.readValues(mockMvc.perform(get(USER_RESTAURANT_URL)
                .with(authenticate(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), Restaurant.class);

        assertTrue(restaurants.size() == 2);
        assertTrue(restaurants.get(0).getName().equals(ADMIN_VOTED_RESTAURANT.getName()));
        assertTrue(restaurants.get(1).getName().equals(USERS_VOTED_RESTAURANT.getName()));

    }


    @Test
    public void doVotingUser2() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            mockMvc.perform(get(USER_RESTAURANT_URL + "/vote/" + ADMIN_VOTED_RESTAURANT.getId())
                    .with(authenticate(USER_2)))
                    .andExpect(status().isOk());

            assertTrue(service.get(ADMIN_VOTED_RESTAURANT.getId()).getVotes().equals(2));
            assertTrue(service.get(USERS_VOTED_RESTAURANT.getId()).getVotes().equals(2));
        } else {
            mockMvc.perform(get(USER_RESTAURANT_URL + "/vote/" + ADMIN_VOTED_RESTAURANT.getId())
                    .with(authenticate(USER_2)))
                    .andExpect(status().isForbidden());
        }
    }
        @Test
        public void doVotingAdmin() throws Exception {
            if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
                mockMvc.perform(get(USER_RESTAURANT_URL + "/vote/" + ADMIN_VOTED_RESTAURANT.getId())
                        .with(authenticate(ADMIN)))
                        .andExpect(status().isOk());

                assertTrue(service.get(ADMIN_VOTED_RESTAURANT.getId()).getVotes().equals(1));
                assertTrue(service.get(USERS_VOTED_RESTAURANT.getId()).getVotes().equals(3));
            } else {
                mockMvc.perform(get(USER_RESTAURANT_URL + "/vote/" + ADMIN_VOTED_RESTAURANT.getId())
                        .with(authenticate(ADMIN)))
                        .andExpect(status().isForbidden());
            }
        }

}
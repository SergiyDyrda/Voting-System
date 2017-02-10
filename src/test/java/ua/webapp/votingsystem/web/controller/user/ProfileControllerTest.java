package ua.webapp.votingsystem.web.controller.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.webapp.votingsystem.UsersTestData;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.to.UserTo;
import ua.webapp.votingsystem.util.UserUtil;
import ua.webapp.votingsystem.web.AbstractControllerTest;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.*;
import static ua.webapp.votingsystem.web.controller.user.ProfileController.PROFILE_URL;


public class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(PROFILE_URL)
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    public void getAdmin() throws Exception {
        mockMvc.perform(get(PROFILE_URL)
                .with(authenticate(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_1));
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(PROFILE_URL)
                .with(authenticate(USER_1)))
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_2, USER_3), service.getAll());
    }

    @Test
    public void updateUser() throws Exception {
        User user = UsersTestData.copyOf(USER_2);
        UserTo updatedUserTo = UserUtil.asTo(user);
        updatedUserTo.setName("NEW_NEW_NEW");
        UserUtil.updateFromTo(user, updatedUserTo);
        mockMvc.perform(put(PROFILE_URL).with(authenticate(USER_2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedUserTo)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(user));
    }

}
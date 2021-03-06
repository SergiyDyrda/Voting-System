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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.TestUtil.mockAuthorize;
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
    public void getAdmin() throws Exception {
        mockAuthorize(ADMIN);
        mockMvc.perform(get(PROFILE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    public void getUser() throws Exception {
        mockAuthorize(USER_1);
        mockMvc.perform(get(PROFILE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_1));
    }

    @Test
    public void deleteUser() throws Exception {
        mockAuthorize(USER_1);
        mockMvc.perform(delete(PROFILE_URL))
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_2, USER_3), service.getAll());
    }

    @Test
    public void updateUser() throws Exception {
        User user = UsersTestData.copyOf(USER_2);
        UserTo updatedUserTo = UserUtil.asTo(user);
        updatedUserTo.setName("NEW_NEW_NEW");
        UserUtil.updateFromTo(user, updatedUserTo);
        mockAuthorize(USER_2);
        mockMvc.perform(put(PROFILE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedUserTo)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(user));
    }

}
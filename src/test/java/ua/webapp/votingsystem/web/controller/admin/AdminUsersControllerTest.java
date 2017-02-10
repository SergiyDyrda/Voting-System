package ua.webapp.votingsystem.web.controller.admin;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.webapp.votingsystem.model.Role;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.util.UserUtil;
import ua.webapp.votingsystem.web.AbstractControllerTest;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.webapp.votingsystem.TestUtil.authenticate;
import static ua.webapp.votingsystem.UsersTestData.*;


public class AdminUsersControllerTest extends AbstractControllerTest {

    public static final String ADMIN_USERS_URL = AdminUsersController.ADMIN_USERS_URL + "/";

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get(ADMIN_USERS_URL)
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(ADMIN, USER_1, USER_2, USER_3));
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(ADMIN_USERS_URL + ADMIN_ID)
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(ADMIN));
    }

    @Test
    public void getUserDenied() throws Exception {
        String contentAsString = mockMvc.perform(get(ADMIN_USERS_URL + ADMIN_ID)
                .with(authenticate(USER_1)))

                .andReturn().getResponse().getContentAsString();
        System.out.println(JsonUtil.readValue(contentAsString, User.class));
    }

    @Test
    public void getUserByEmail() throws Exception {
        mockMvc.perform(post(ADMIN_USERS_URL + "/em").param("email", USER_1.getEmail())
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER_1));
    }

    public void getUserByEmailUnauthorized() throws Exception {
        mockMvc.perform(post(ADMIN_USERS_URL + "/em").param("email", USER_1.getEmail()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createUser() throws Exception {
        User newUser = new User(null, "newUser", "new@email.com", "passs", Role.ROLE_USER);

        User created = JsonUtil.readValue(mockMvc.perform(post(ADMIN_USERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UserUtil.asTo(newUser)))
                .with(authenticate(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), User.class);

        newUser.setId(created.getId());

        MATCHER.assertEquals(created, newUser);
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(ADMIN_USERS_URL + USER_2_ID)
                .with(authenticate(ADMIN)))
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_1, USER_3), service.getAll());
    }

    @Test
    public void updateUser() throws Exception {
        String newEmail = "newemail@google.com";
        User updatedUser = copyOf(USER_1);
        updatedUser.setEmail(newEmail);
        mockMvc.perform(put(ADMIN_USERS_URL + USER_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UserUtil.asTo(updatedUser)))
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(updatedUser));

        assertEquals(service.get(USER_1_ID).getEmail(), newEmail);

    }

    @Test
    public void enabled() throws Exception {
        mockMvc.perform(patch(ADMIN_USERS_URL + '/' + USER_2_ID + '/' + false)
                .with(authenticate(ADMIN)))
                .andExpect(status().isOk());

        assertFalse(service.get(USER_2_ID).isEnabled());
    }
}
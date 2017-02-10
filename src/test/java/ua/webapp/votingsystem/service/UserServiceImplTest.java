package ua.webapp.votingsystem.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ua.webapp.votingsystem.UsersTestData;
import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.Role;
import ua.webapp.votingsystem.model.User;

import java.util.Arrays;

import static ua.webapp.votingsystem.UsersTestData.*;


public class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService.evictCache();
    }

    @Test
    public void save() throws Exception {
        User user = new User(null, "New", "new@mail.com", "passsss", true, Role.ROLE_USER);
        User created = userService.save(user);
        user.setId(created.getId());
        UsersTestData.MATCHER.assertEquals(created, user);
    }

    @Test(expected = DataAccessException.class)
    public void saveDuplicateMail() throws Exception {
        User user = new User(null, "New", USER_3.getEmail(), "passsss", true, Role.ROLE_USER);
        userService.save(user);
    }

    @Test
    public void delete() throws Exception {
        userService.delete(UsersTestData.USER_1_ID);
        UsersTestData.MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_2, USER_3),
                userService.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        userService.delete(UsersTestData.USER_1_ID - 3);
    }

    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(ADMIN, userService.get(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(USER_1_ID + 5);
    }

    @Test
    public void getByEmail() throws Exception {
        MATCHER.assertEquals(USER_2, userService.getByEmail(USER_2.getEmail()));
    }

    @Test(expected = NotFoundException.class)
    public void getByEmailNotFound() throws Exception {
        userService.getByEmail(USER_2.getEmail() + "111");
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, USER_1, USER_2, USER_3),
                userService.getAll());
    }

    @Test
    public void update() throws Exception {
        User updated = copyOf(USER_1);
        updated.setName("NewName");
        userService.update(updated);
        MATCHER.assertEquals(USER_1, userService.get(USER_1_ID));
    }

    @Test
    public void enable() throws Exception {
        userService.enable(USER_1_ID, false);
        Assert.assertFalse(userService.get(USER_1_ID).isEnabled());

        userService.enable(USER_1_ID, true);
        Assert.assertTrue(userService.get(USER_1_ID).isEnabled());    }

}
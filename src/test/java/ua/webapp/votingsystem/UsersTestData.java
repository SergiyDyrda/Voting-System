package ua.webapp.votingsystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.webapp.votingsystem.matcher.ModelMatcher;
import ua.webapp.votingsystem.model.Role;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.util.PasswordUtil;

import java.util.Objects;

import static ua.webapp.votingsystem.model.BaseEntity.START_SEQ;

public class UsersTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UsersTestData.class);

    public static final Integer ADMIN_ID = START_SEQ + 8;
    public static final Integer USER_1_ID = START_SEQ + 9;
    public static final Integer USER_2_ID = START_SEQ + 10;
    public static final Integer USER_3_ID = START_SEQ + 11;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final User USER_1 = new User(USER_1_ID, "User_1", "user@yandex.ru", "user1", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_2_ID, "User_2", "user@hotmail.ru", "user2", Role.ROLE_USER);
    public static final User USER_3 = new User(USER_3_ID, "User_3", "user@mail.ru", "user3", Role.ROLE_USER);

    public static User copyOf(User user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getRoles());
    }


    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            ((expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())) &&
                            Objects.equals(expected.getId(), actual.getId()) &&
                            Objects.equals(expected.getName(), actual.getName()) &&
                            Objects.equals(expected.getEmail(), actual.getEmail()) &&
                            Objects.equals(expected.isEnabled(), actual.isEnabled()) &&
                            Objects.equals(expected.getRoles(), actual.getRoles())));

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }

}

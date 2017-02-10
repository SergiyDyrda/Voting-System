package ua.webapp.votingsystem.repository;


import ua.webapp.votingsystem.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();
}

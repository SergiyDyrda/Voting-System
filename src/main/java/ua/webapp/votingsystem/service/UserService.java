package ua.webapp.votingsystem.service;

import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.to.UserTo;

import java.util.List;


public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    User update(UserTo user);

    void evictCache();

    void enable(int id, boolean enable);

}

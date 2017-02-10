package ua.webapp.votingsystem.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.to.UserTo;

import java.util.List;




public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public AbstractUserController(UserService service) {
        this.service = service;
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get " + id);
        return service.get(id);
    }

    public User create(User user) {
        user.setId(null);
        log.info("create " + user);
        return service.save(user);
    }

    public void delete(int id) {
        log.info("delete " + id);
        service.delete(id);
    }

    public void update(User user, int id) {
        user.setId(id);
        log.info("update " + user);
        service.update(user);
    }

    public User update(UserTo userTo) {
        log.info("update " + userTo);
        return service.update(userTo);
    }


    public User getByMail(String email) {
        log.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }
}

package ua.webapp.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.webapp.votingsystem.AuthorizedUser;
import ua.webapp.votingsystem.exception.NotFoundException;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.repository.UserRepository;
import ua.webapp.votingsystem.to.UserTo;
import ua.webapp.votingsystem.util.ExceptionUtil;

import java.util.List;

import static ua.webapp.votingsystem.util.UserUtil.prepareToSave;
import static ua.webapp.votingsystem.util.UserUtil.updateFromTo;


@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        Assert.notNull(user, "users must not be null");
        return repository.save(prepareToSave(user));
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "Email must not be null");
        return ExceptionUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "users must not be null");
        repository.save(prepareToSave(user));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public User update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        return repository.save(prepareToSave(user));
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void evictCache() {

    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(prepareToSave(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }
}

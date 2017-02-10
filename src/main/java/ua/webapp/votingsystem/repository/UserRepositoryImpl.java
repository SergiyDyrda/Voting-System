package ua.webapp.votingsystem.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.webapp.votingsystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User save(User user) {
    if (user.isNew()) {
        entityManager.persist(user);
        return user;
    } else
        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
       return DataAccessUtils.singleResult(
               entityManager.createNamedQuery(User.GET_BY_EMAIL, User.class)
               .setParameter("email", email)
               .getResultList());
    }

    @Override
    public List<User> getAll() {
        return entityManager.createNamedQuery(User.GET_ALL, User.class).getResultList();
    }
}

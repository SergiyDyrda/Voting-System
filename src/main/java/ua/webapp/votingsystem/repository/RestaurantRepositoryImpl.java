package ua.webapp.votingsystem.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.webapp.votingsystem.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            entityManager.persist(restaurant);
            return restaurant;
        } else {
            return entityManager.merge(restaurant);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Restaurant get(int id) {
        return DataAccessUtils.singleResult(
                entityManager.createNamedQuery(Restaurant.GET, Restaurant.class)
                        .setParameter("id", id)
                        .getResultList());
    }

    @Override
    public List<Restaurant> getAll() {
        return entityManager.createNamedQuery(Restaurant.GET_ALL, Restaurant.class).getResultList();
    }

    @Override
    public List<Restaurant> getAllWithDishes() {
        return entityManager.createNamedQuery(Restaurant.GET_ALL_WITH_DISHES, Restaurant.class).getResultList();
    }
}

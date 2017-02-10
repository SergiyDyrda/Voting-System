package ua.webapp.votingsystem.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class DishRepositoryImpl implements DishRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null)
            return null;

        dish.setRestaurant(entityManager.getReference(Restaurant.class, restaurantId));
        if (dish.isNew()) {
            entityManager.persist(dish);
            return dish;
        } else {
            return entityManager.merge(dish);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return entityManager.createNamedQuery(Dish.DELETE)
                .setParameter("restaurantId", restaurantId)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        Dish dish = entityManager.find(Dish.class, id);
        return dish != null ? dish.getRestaurant().getId() == restaurantId ? dish : null : null;
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return entityManager.createNamedQuery(Dish.GET_ALL, Dish.class).
                setParameter("restaurantId", restaurantId).getResultList();
    }
}

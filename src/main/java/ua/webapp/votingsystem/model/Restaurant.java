package ua.webapp.votingsystem.model;


import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant rest WHERE rest.id=:id"),
        @NamedQuery(name = Restaurant.GET, query = "SELECT DISTINCT rest FROM Restaurant rest JOIN FETCH rest.dishes WHERE rest.id=:id"),
        @NamedQuery(name = Restaurant.GET_ALL, query = "SELECT rest FROM Restaurant rest"),
        @NamedQuery(name = Restaurant.GET_ALL_WITH_DISHES, query = "SELECT DISTINCT rest FROM Restaurant rest LEFT JOIN FETCH rest.dishes ORDER BY rest.id")
})
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String GET_ALL = "Restaurant.getAll";
    public static final String GET_ALL_WITH_DISHES = "Restaurant.getAllWithDishes";
    public static final String GET = "Restaurant.get";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "restaurant")
    @CollectionTable(name = "dishes", joinColumns = @JoinColumn(name = "restaurant_id"))
    private Set<Dish> dishes;

    @Column(name = "votes", nullable = false)
    private Integer votes;

    public Restaurant() {
        votes = 0;
    }

    public Restaurant(Integer id, String name, Dish... dishes) {
        this(id, name, new HashSet<>(Arrays.asList(dishes)));
    }

    public Restaurant(Integer id, String name, Set<Dish> dishes) {
        super(id, name);
        this.dishes = dishes;
        votes = 0;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}

package ua.webapp.votingsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish m WHERE m.restaurant.id=:restaurantId AND m.id=:id"),
        @NamedQuery(name = Dish.GET_ALL, query = "SELECT m FROM Dish m WHERE m.restaurant.id=:restaurantId")
})
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id"}, name = "meals_unique_restaurant_idx")})
public class Dish extends NamedEntity {

    public static final String DELETE = "Dish.delete";
    public static final String GET_ALL = "Dish.getAll";

    @Column(name = "description", nullable = false)
    @SafeHtml
    private String description;

    @Column(name = "calories", nullable = false)
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, String description, Integer calories) {
        this(null, name, description, calories);
    }

    public Dish(Integer id, String name, String description, Integer calories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", name='" + description + '\'' +
                '}';
    }
}

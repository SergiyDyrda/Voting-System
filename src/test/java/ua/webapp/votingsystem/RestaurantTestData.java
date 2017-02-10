package ua.webapp.votingsystem;


import ua.webapp.votingsystem.matcher.ModelMatcher;
import ua.webapp.votingsystem.model.Restaurant;

import java.util.Objects;

import static ua.webapp.votingsystem.DishTestData.*;
import static ua.webapp.votingsystem.model.BaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final Restaurant ADMIN_VOTED_RESTAURANT = new Restaurant(START_SEQ, "Claud Mone",
            ADMIN_VOTED_RESTAURANT_DISH_1, ADMIN_VOTED_RESTAURANT_DISH_2, ADMIN_VOTED_RESTAURANT_DISH_3);

    public static final Restaurant USERS_VOTED_RESTAURANT = new Restaurant(START_SEQ + 1, "Puzata hata",
            USERS_VOTED_RESTAURANT_DISH_1, USERS_VOTED_RESTAURANT_DISH_2, USERS_VOTED_RESTAURANT_DISH_3);

    static {
        ADMIN_VOTED_RESTAURANT.setVotes(1);
        USERS_VOTED_RESTAURANT.setVotes(3);
    }

    public static Restaurant copyOf(Restaurant restaurant) {
        return new Restaurant(restaurant.getId(), restaurant.getName(), restaurant.getDishes());
    }
    public static final ModelMatcher<Restaurant> MATCHER = ModelMatcher.of(Restaurant.class,
            (expected, actual) ->
                    expected == actual ||
                            Objects.equals(expected.getId(), actual.getId()) &&
                                    Objects.equals(expected.getName(), actual.getName()) &&
                                    Objects.equals(expected.getDishes(), actual.getDishes()));
}

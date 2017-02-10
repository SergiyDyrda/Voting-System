package ua.webapp.votingsystem;

import ua.webapp.votingsystem.matcher.ModelMatcher;
import ua.webapp.votingsystem.model.Dish;

import java.util.Objects;

import static ua.webapp.votingsystem.model.BaseEntity.START_SEQ;

public class DishTestData {

    public static final Dish ADMIN_VOTED_RESTAURANT_DISH_1 = new Dish(START_SEQ + 2, "Baked potato", "Perfect your spud technique then pile our filling ideas high", 500);
    public static final Dish ADMIN_VOTED_RESTAURANT_DISH_2 = new Dish(START_SEQ + 3, "Spicy turkey sweet potatoes", "Transform turkey thigh mince into a low-fat, low-calorie, chilli-style filling for soft, creamy baked sweet potatoes", 600);
    public static final Dish ADMIN_VOTED_RESTAURANT_DISH_3 = new Dish(START_SEQ + 4, "Pizza baked potato", "This budget-friendly supper combines two favourites in one dish. Top your jacket spuds with cheese, tomato, pepperoni and basil", 700);

    public static final Dish USERS_VOTED_RESTAURANT_DISH_1 = new Dish(START_SEQ + 5, "Baked potatoes with spicy dhal", "Cook red lentils with cumin, mustard seeds and turmeric and serve with a fluffy jacket potato and chutney", 800);
    public static final Dish USERS_VOTED_RESTAURANT_DISH_2 = new Dish(START_SEQ + 6, "Turkey chilli jacket potatoes", "Turkey mince is cheap and lean - flavour Mexican-style with cumin and paprika and serve in crisp baked potatoes", 900);
    public static final Dish USERS_VOTED_RESTAURANT_DISH_3 = new Dish(START_SEQ + 7, "Classic jacket potatoes", "Make the perfect jacket potato - crispy on the outside and meltingly soft in the middle", 1000);

    public static final ModelMatcher<Dish> MATCHER = ModelMatcher.of(Dish.class,
            (expected, actual) -> expected == actual ||
                    Objects.equals(expected.getId(), actual.getId()) &&
                            Objects.equals(expected.getName(), actual.getName()) &&
                            Objects.equals(expected.getCalories(), actual.getCalories()) &&
                            Objects.equals(expected.getDescription(), actual.getDescription()));

    public static Dish copyOf(Dish dish) {
        return new Dish(dish.getId(), dish.getName(), dish.getDescription(), dish.getCalories());
    }
}

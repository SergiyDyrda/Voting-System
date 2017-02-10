package ua.webapp.votingsystem.web.controller.user;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.service.DishService;
import ua.webapp.votingsystem.web.controller.AbstractDishController;

import java.util.List;

import static ua.webapp.votingsystem.web.controller.user.UserRestaurantController.USER_RESTAURANT_URL;

@RestController
@RequestMapping(path = DishController.USER_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController extends AbstractDishController {

    public static final String USER_DISH_URL = USER_RESTAURANT_URL + "/{restaurantId}/dishes";

    public DishController(DishService service) {
        super(service);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(super.get(id, restaurantId));
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDish(@PathVariable("restaurantId") Integer restaurantId) {
        return ResponseEntity.ok(super.getAll(restaurantId));
    }

}

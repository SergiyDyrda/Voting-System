package ua.webapp.votingsystem.web.controller.admin;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.webapp.votingsystem.model.Dish;
import ua.webapp.votingsystem.service.DishService;
import ua.webapp.votingsystem.web.controller.AbstractDishController;

import javax.validation.Valid;
import java.net.URI;

import static ua.webapp.votingsystem.web.controller.admin.AdminRestaurantController.ADMIN_RESTAURANT_URL;
import static ua.webapp.votingsystem.web.controller.user.DishController.USER_DISH_URL;


@RestController
@RequestMapping(path = AdminDishController.ADMIN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractDishController {

    public static final String ADMIN_DISH_URL = ADMIN_RESTAURANT_URL + "/{restaurantId}/dishes";


    public AdminDishController(DishService service) {
        super(service);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@PathVariable("restaurantId") Integer restaurantId, @Valid @RequestBody Dish dish) {
        Dish created = super.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USER_DISH_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> updateRestaurant(@PathVariable("restaurantId") Integer restaurantId,
                                                 @Valid @RequestBody Dish dish) {
        super.update(dish, restaurantId);
        return ResponseEntity.ok(dish);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("id") Integer id) {
        super.delete(id, restaurantId);
        return ResponseEntity.noContent().build();
    }
}

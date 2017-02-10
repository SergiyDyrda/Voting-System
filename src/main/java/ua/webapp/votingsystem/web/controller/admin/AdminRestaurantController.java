package ua.webapp.votingsystem.web.controller.admin;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.service.RestaurantService;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.web.controller.AbstractRestaurantController;

import javax.validation.Valid;
import java.net.URI;

import static ua.webapp.votingsystem.web.controller.user.UserRestaurantController.USER_RESTAURANT_URL;

@RestController
@RequestMapping(path = AdminRestaurantController.ADMIN_RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String ADMIN_RESTAURANT_URL = "/admin/restaurants";

    public AdminRestaurantController(RestaurantService service, UserService userService) {
        super(service, userService);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        restaurant.setVotes(0);
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USER_RESTAURANT_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody Restaurant restaurant) {
        super.update(restaurant);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable("id") Integer id) {
        super.delete(id);
        return ResponseEntity.noContent().build();
    }

}

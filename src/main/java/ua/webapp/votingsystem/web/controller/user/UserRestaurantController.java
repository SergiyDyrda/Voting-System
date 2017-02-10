package ua.webapp.votingsystem.web.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.webapp.votingsystem.exception.TooLateToVoteException;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.service.RestaurantService;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.web.controller.AbstractRestaurantController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(UserRestaurantController.USER_RESTAURANT_URL)
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String USER_RESTAURANT_URL = "/restaurants";

    @Autowired
    private MessageSource messageSource;

    public UserRestaurantController(RestaurantService service, UserService userService) {
        super(service, userService);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(super.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok().body(super.getAll());
    }

    @GetMapping("/vote/{id}")
    public ResponseEntity<Void> doVoting(@PathVariable Integer id) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            super.vote(id);
            return ResponseEntity.ok().build();
        } else {
            throw new TooLateToVoteException(messageSource.getMessage("exception.too_late", null, LocaleContextHolder.getLocale()));
        }

    }
}

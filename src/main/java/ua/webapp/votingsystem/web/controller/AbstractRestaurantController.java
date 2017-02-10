package ua.webapp.votingsystem.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.webapp.votingsystem.AuthorizedUser;
import ua.webapp.votingsystem.model.Restaurant;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.RestaurantService;
import ua.webapp.votingsystem.service.UserService;

import java.util.List;
import java.util.Objects;

import static ua.webapp.votingsystem.model.VotesAction.DECREMENT;
import static ua.webapp.votingsystem.model.VotesAction.INCREMENT;

public abstract class AbstractRestaurantController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private final RestaurantService service;

    private final UserService userService;

    @Autowired
    public AbstractRestaurantController(RestaurantService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public List<Restaurant> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public Restaurant get(Integer id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        restaurant.setId(null);
        LOG.info("create " + restaurant);
        return service.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        LOG.info("update " + restaurant);
        service.update(restaurant);
    }

    public void delete(Integer id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void vote(Integer id) {
        LOG.info("vote for " + id);
        User authorizedUser = userService.get(AuthorizedUser.id());
        Integer votedRestaurantId = authorizedUser.getVotedRestaurant().getId();

        if (votedRestaurantId != null) {
            if (Objects.equals(votedRestaurantId, id)) return;
            service.changeNumberOfVotes(votedRestaurantId, DECREMENT);
        }

        service.changeNumberOfVotes(id, INCREMENT);
        authorizedUser.getVotedRestaurant().setId(id);
        userService.update(authorizedUser);
    }

}

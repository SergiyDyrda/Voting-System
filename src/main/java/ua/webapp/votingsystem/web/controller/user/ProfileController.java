package ua.webapp.votingsystem.web.controller.user;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.webapp.votingsystem.AuthorizedUser;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.to.UserTo;
import ua.webapp.votingsystem.web.controller.AbstractUserController;

import javax.validation.Valid;

@RestController
@RequestMapping(ProfileController.PROFILE_URL)
public class ProfileController extends AbstractUserController {

    public static final String PROFILE_URL = "/profile";

    public ProfileController(UserService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get() {
        return ResponseEntity.ok(super.get(AuthorizedUser.id()));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        super.delete(AuthorizedUser.id());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserTo userTo) {
        userTo.setId(AuthorizedUser.id());
        return ResponseEntity.ok(super.update(userTo));
    }
}

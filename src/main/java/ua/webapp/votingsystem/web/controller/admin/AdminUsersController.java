package ua.webapp.votingsystem.web.controller.admin;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.webapp.votingsystem.AuthorizedUser;
import ua.webapp.votingsystem.model.User;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.to.UserTo;
import ua.webapp.votingsystem.util.UserUtil;
import ua.webapp.votingsystem.web.controller.AbstractUserController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ua.webapp.votingsystem.web.controller.user.ProfileController.PROFILE_URL;

@RestController
@RequestMapping(path = AdminUsersController.ADMIN_USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUsersController extends AbstractUserController {

    public static final String ADMIN_USERS_URL = "/admin/users";

    public AdminUsersController(UserService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(super.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(super.get(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/em")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(super.getByMail(email), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserTo user) {
        User created = super.create(UserUtil.createNewFromTo(user));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PROFILE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        super.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserTo user, @PathVariable("id") Integer id) {
        AuthorizedUser.get().update(user);
        return new ResponseEntity<>(super.update(user), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/{enabled}")
    public ResponseEntity<Void> enabled(@PathVariable("id") int id, @PathVariable("enabled") boolean enabled) {
        super.enable(id, enabled);
        return ResponseEntity.ok().build();
    }
}

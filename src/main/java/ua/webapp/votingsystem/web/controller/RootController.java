package ua.webapp.votingsystem.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.webapp.votingsystem.service.UserService;
import ua.webapp.votingsystem.to.UserTo;
import ua.webapp.votingsystem.util.UserUtil;
import ua.webapp.votingsystem.web.resources.CustomReloadableResourceBundleMessageSource;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Properties;


@RestController
public class RootController extends AbstractUserController {

    private final CustomReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    @Autowired
    public RootController(UserService service, CustomReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
        super(service);
        this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
    }

    @PostMapping("/register")
    public void saveRegister(@Valid @RequestBody UserTo userTo) {
        super.create(UserUtil.createNewFromTo(userTo));
    }

    @RequestMapping(value = "/i18n/{locale}", method = RequestMethod.GET)
    public Properties getLocal(@PathVariable String locale) {
        return reloadableResourceBundleMessageSource.getAllMessages(new Locale(locale));
    }

}

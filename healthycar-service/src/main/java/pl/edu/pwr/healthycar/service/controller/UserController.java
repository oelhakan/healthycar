package pl.edu.pwr.healthycar.service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.persistence.model.LoginInfo;
import pl.edu.pwr.healthycar.persistence.model.User;
import pl.edu.pwr.healthycar.service.service.UserService;
import pl.edu.pwr.healthycar.service.utilities.Endpoints;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(Endpoints.USERS)
    public List<User> getUsers() {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS));
        List<User> users = userService.getUsers();
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS) + users);
        return users;
    }

    @GetMapping(Endpoints.USERS_ID)
    public User getUser(@PathVariable String id) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_ID) + id);
        User user = userService.getUser(id);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_ID) + user);
        return user;
    }

    @PostMapping(Endpoints.USERS_SAVE)
    public User upsertUser(@RequestBody User user) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_SAVE));
        User savedUser = userService.upsertUser(user);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_SAVE) + savedUser);
        return savedUser;
    }

    @DeleteMapping(Endpoints.USERS_DELETE_ID)
    public String deleteUser(@PathVariable String id) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_DELETE_ID) + id);
        String deleteResult = userService.deleteUser(id);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_DELETE_ID) + deleteResult);
        return deleteResult;
    }

    @PostMapping(Endpoints.USERS_LOGIN)
    public User login(@RequestBody LoginInfo loginInfo) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_LOGIN));
        User user = userService.login(loginInfo);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_LOGIN) + user);
        return user;
    }

    @GetMapping(Endpoints.USERS_RESET_EMAIL)
    public String resetPassword(@PathVariable String email){
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_RESET_EMAIL) + email);
        String resetResult = userService.resetPassword(email);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_RESET_EMAIL) + resetResult);
        return resetResult;
    }
}

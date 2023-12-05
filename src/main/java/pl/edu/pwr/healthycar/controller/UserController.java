package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.LoginInfo;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.service.UserService;

import java.util.List;

import static pl.edu.pwr.healthycar.utilities.Endpoints.*;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(USERS)
    public List<User> getUsers() {
        log.debug(buildRequestLog(USERS));
        List<User> users = userService.getUsers();
        log.debug(buildResponseLog(USERS) + users);
        return users;
    }

    @GetMapping(USERS_ID)
    public User getUser(@PathVariable String id) {
        log.debug(buildRequestLog(USERS_ID) + id);
        User user = userService.getUser(id);
        log.debug(buildResponseLog(USERS_ID) + user);
        return user;
    }

    @PostMapping(USERS_SAVE)
    public User upsertUser(@RequestBody User user) {
        log.debug(buildRequestLog(USERS_SAVE));
        User savedUser = userService.upsertUser(user);
        log.debug(buildResponseLog(USERS_SAVE) + savedUser);
        return savedUser;
    }

    @DeleteMapping(USERS_DELETE_ID)
    public String deleteUser(@PathVariable String id) {
        log.debug(buildRequestLog(USERS_DELETE_ID) + id);
        String deleteResult = userService.deleteUser(id);
        log.debug(buildResponseLog(USERS_DELETE_ID) + deleteResult);
        return deleteResult;
    }

    @PostMapping(USERS_LOGIN)
    public User login(@RequestBody LoginInfo loginInfo) {
        log.debug(buildRequestLog(USERS_LOGIN));
        User user = userService.login(loginInfo);
        log.debug(buildResponseLog(USERS_LOGIN) + user);
        return user;
    }

    @GetMapping(USERS_RESET_EMAIL)
    public String resetPassword(@PathVariable String email){
        log.debug(buildRequestLog(USERS_RESET_EMAIL) + email);
        String resetResult = userService.resetPassword(email);
        log.debug(buildResponseLog(USERS_RESET_EMAIL) + resetResult);
        return resetResult;
    }
}

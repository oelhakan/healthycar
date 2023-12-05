package pl.edu.pwr.healthycar.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.User;
import pl.edu.pwr.healthycar.api.model.LoginInfo;
import pl.edu.pwr.healthycar.api.service.UserApi;
import pl.edu.pwr.healthycar.service.service.UserService;
import pl.edu.pwr.healthycar.api.utilities.Endpoints;

import java.util.List;

@RestController
@Slf4j
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public List<User> getUsers() {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS));
        List<User> users = userService.getUsers();
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS) + users);
        return users;
    }

    @Override
    public User getUser(String id) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_ID) + id);
        User user = userService.getUser(id);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_ID) + user);
        return user;
    }

    @Override
    public User upsertUser(User user) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_SAVE));
        User savedUser = userService.upsertUser(user);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_SAVE) + savedUser);
        return savedUser;
    }

    @Override
    public String deleteUser(String id) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_DELETE_ID) + id);
        String deleteResult = userService.deleteUser(id);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_DELETE_ID) + deleteResult);
        return deleteResult;
    }

    @Override
    public User login(LoginInfo loginInfo) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_LOGIN));
        User user = userService.login(loginInfo);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_LOGIN) + user);
        return user;
    }

    @Override
    public String resetPassword(String email) {
        log.debug(Endpoints.buildRequestLog(Endpoints.USERS_RESET_EMAIL) + email);
        String resetResult = userService.resetPassword(email);
        log.debug(Endpoints.buildResponseLog(Endpoints.USERS_RESET_EMAIL) + resetResult);
        return resetResult;
    }
}

package pl.edu.pwr.healthycar.api.service;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.LoginInfo;
import pl.edu.pwr.healthycar.api.model.ResetInfo;
import pl.edu.pwr.healthycar.api.model.User;
import pl.edu.pwr.healthycar.api.utilities.Endpoints;

import java.util.List;

public interface UserApi {

    @GetMapping(Endpoints.USERS)
    List<User> getUsers();

    @GetMapping(Endpoints.USERS_ID)
    User getUser(@PathVariable String id);

    @PostMapping(Endpoints.USERS_SAVE)
    User upsertUser(@RequestBody User user);

    @DeleteMapping(Endpoints.USERS_DELETE_ID)
    String deleteUser(@PathVariable String id);

    @PostMapping(Endpoints.USERS_LOGIN)
    User login(@RequestBody LoginInfo loginInfo);

    @PostMapping(Endpoints.USERS_RESET)
    String resetPassword(@RequestBody ResetInfo resetInfo);
}

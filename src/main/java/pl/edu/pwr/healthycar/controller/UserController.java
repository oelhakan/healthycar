package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public List<User> getStudents(){
        return userService.getAllUsers();
    }
}

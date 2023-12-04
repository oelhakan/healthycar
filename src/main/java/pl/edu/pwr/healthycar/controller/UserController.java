package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.model.LoginInfo;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(new ObjectId(id)).orElse(null);
    }

    @PostMapping("/users/add")
    public User addUser(@RequestBody User user) {
        user.setCarCount(0);
        user.setIsAdmin(false);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "User " + id + " deleted successfully.";
    }

    @PostMapping("/users/login")
    public User login(@RequestBody LoginInfo loginInfo) {
        Optional<User> user = userRepository.findByEmail(loginInfo.getEmail());
        if(user.isPresent()){
            if(BCrypt.checkpw(loginInfo.getPassword(), user.get().getPassword())){
                return user.get();
            }else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Passwords do not match!");
            }
        }else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "No user found with email " + loginInfo.getEmail() + "!");
        }
    }
}

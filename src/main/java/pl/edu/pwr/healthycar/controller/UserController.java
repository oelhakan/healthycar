package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.repository.UserRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
//        [
//        {
//            "id": "655f6c358cb277402a49f485",
//                "firstName": "Onur",
//                "lastName": "Elhakan",
//                "email": "onurelhakan@gmail.com",
//                "password": "Onur123*",
//                "isAdmin": false,
//                "isFO": false,
//                "carCount": 5
//        },
//        {
//            "id": "65688c309c19610f75dda1f9",
//                "firstName": "Atahan",
//                "lastName": "Ergurhan",
//                "email": "atahanergurhan@gmail.com",
//                "password": "Atahan123*",
//                "isAdmin": false,
//                "isFO": false,
//                "carCount": 5
//        }
//]
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(new ObjectId(id)).orElse(null);
//        {
//            "id": "656893717f3e3013acdff660",
//                "firstName": "Atahan",
//                "lastName": "Ergurhan",
//                "email": "atahanergurhan@gmail.com",
//                "password": "Atahan123*",
//                "isAdmin": false,
//                "isFO": false,
//                "carCount": 5
//        }
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
//        {
//            "id": "65688c309c19610f75dda1f9",
//                "firstName": "Atahan",
//                "lastName": "Ergurhan",
//                "email": "atahanergurhan@gmail.com",
//                "password": "Atahan123*",
//                "isAdmin": false,
//                "isFO": false,
//                "carCount": 5
//        }
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "User " + id + " deleted successfully.";
        //User 655f6c358cb277402a49f485 deleted successfully.
    }

    //TODO - Edit User
}

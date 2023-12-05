package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.model.LoginInfo;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.repository.UserRepository;
import pl.edu.pwr.healthycar.service.UserService;
import pl.edu.pwr.healthycar.utilities.HealthyCarUtilities;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.debug("REQ => /users");
        List<User> users = userRepository.findAll();
        log.debug(String.format("Queried DB for users. Found %d %s", users.size(), users.size() == 1 ? "user." : "users."));
        log.debug("RES => " + users);
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        log.debug("REQ => /users/" + id);
        Optional<User> user = userRepository.findById(new ObjectId(id));
        log.debug("Queried DB for user with ID " + id + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        log.debug("RES => " + user.orElse(null));
        return user.orElse(null);
    }

    @PostMapping("/users/save")
    public User upsertUser(@RequestBody User user) {
        log.debug("REQ => /users/add");
        log.debug("Adding user with request body " + user);

        try{
            Optional<User> dbUser = userRepository.findById(user.getId());
            if(dbUser.isPresent()){
                log.debug("User with ID " + user.getId() + " already exists. Updated the record.");
            }else{
                log.debug("User with ID " + user.getId() + " does not exist. Created new record.");
                log.debug("Set default values to carCount(0) and isAdmin(false).");
                user.setCarCount(0);
                user.setIsAdmin(false);
            }
        }catch(IllegalArgumentException ex){
            log.debug("User ID was not provided in request body. Created new record with auto generated ID.");
            log.debug("Set default values to carCount(0) and isAdmin(false).");
            user.setCarCount(0);
            user.setIsAdmin(false);
        }
        log.debug("Hashing user password. Before hashing : " + user.getPassword());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        log.debug("Finished hashing user password. After hashing : " + hashedPassword);
        User savedUser = userRepository.save(user);

        log.debug("RES => " + savedUser);
        return savedUser;
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        log.debug("REQ => /users/delete/" + id);
        Optional<User> user = userRepository.findById(new ObjectId(id));
        log.debug("Queried DB for user with ID " + id + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        if(user.isPresent()){
            userRepository.deleteById(id);
            log.debug("Deleted user with ID " + id + " from DB.");
            log.debug("RES => User with ID " + id + " deleted successfully.");
            return "User with ID " + id + " deleted successfully.";
        }else{
            log.debug("RES => User with ID " + id + " is not present in DB.");
            return "User with ID " + id + " is not present in DB.";
        }
    }

    @PostMapping("/users/login")
    public User login(@RequestBody LoginInfo loginInfo) {
        log.debug("REQ => /users/login");
        log.debug("Log in info : " + loginInfo);
        Optional<User> user = userRepository.findByEmail(loginInfo.getEmail());
        log.debug("Queried DB for user with email " + loginInfo.getEmail() + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        if(user.isPresent()){
            log.debug("User : " + user.get());
            if(BCrypt.checkpw(loginInfo.getPassword(), user.get().getPassword())){
                log.debug("Password " + loginInfo.getPassword() + " matches users password. Log in successful.");
                log.debug("RES => " + user.get());
                return user.get();
            }else{
                log.debug("Password " + loginInfo.getPassword() + " does not match users password. Log in failed.");
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Passwords do not match!");
            }
        }else{
            log.debug("No user found with email " + loginInfo.getEmail() + ". Log in failed.");
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "No user found with email " + loginInfo.getEmail() + "!");
        }
    }

    @GetMapping("/users/reset/{email}")
    public String resetPassword(@PathVariable String email){
        log.debug("REQ => /users/reset/" + email);
        Optional<User> user = userRepository.findByEmail(email);
        log.debug("Queried DB for user with email " + email + ". User " + (user.isPresent() ? "exists." : "does not exist."));

        if(user.isPresent()){
            log.debug("User : " + user.get());
            User savedUser = user.get();
            String generatedPassword = HealthyCarUtilities.generatePassword();
            log.debug("Generated random password for " + email + ".");
            log.debug("Hashing user password. Before hashing : " + generatedPassword);
            String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());
            savedUser.setPassword(hashedPassword);
            log.debug("Finished hashing user password. After hashing : " + hashedPassword);
            userRepository.save(savedUser);
            log.debug("Password reset successful for user " + savedUser);
            userService.sendSimpleMessage(email, "HealthyCar Password Reset", generatedPassword);
            log.debug("RES => Password reset successful for user " + savedUser.getEmail() + ". Your new password has been sent to your email.");
            return "Password reset successful for user " + savedUser.getEmail() + ". Your new password has been sent to your email.";
        }else{
            log.debug("No user found with email " + email + ". Password reset failed.");
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "No user found with email " + email + "!");
        }
    }
}

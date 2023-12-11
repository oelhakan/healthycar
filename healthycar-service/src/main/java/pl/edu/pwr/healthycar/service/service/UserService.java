package pl.edu.pwr.healthycar.service.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.api.model.PasswordChange;
import pl.edu.pwr.healthycar.api.model.PasswordReset;
import pl.edu.pwr.healthycar.api.model.User;
import pl.edu.pwr.healthycar.api.model.LoginInfo;
import pl.edu.pwr.healthycar.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.passay.AllowedCharacterRule.ERROR_CODE;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.debug(String.format("Queried DB for users. Found %d users.", users.size()));
        return users;
    }

    public User getOne(String id) {
        Optional<User> user = userRepository.findById(new ObjectId(id));
        log.debug("Queried DB for user with ID " + id + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        return user.orElse(null);
    }

    public User upsert(User user) {
        log.debug("Adding user with request body " + user);

        try {
            Optional<User> dbUser = userRepository.findById(user.getId());
            if (dbUser.isPresent()) {
                log.debug("User with ID " + user.getId() + " already exists. Updated the record.");
            } else {
                log.debug("User with ID " + user.getId() + " does not exist. Created new record.");
                log.debug("Set default values to carCount(0) and isAdmin(false).");
                user.setCarCount(0);
                user.setIsAdmin(false);
            }
        } catch (IllegalArgumentException ex) {
            log.debug("User ID was not provided in request body. Created new record with auto generated ID.");
            log.debug("Set default values to carCount(0) and isAdmin(false).");
            user.setCarCount(0);
            user.setIsAdmin(false);
        }
        log.debug("Hashing user password. Before hashing : " + user.getPassword());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        log.debug("Finished hashing user password. After hashing : " + hashedPassword);

        return userRepository.save(user);
    }

    public String delete(String id) {
        Optional<User> user = userRepository.findById(new ObjectId(id));
        log.debug("Queried DB for user with ID " + id + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        if (user.isPresent()) {
            userRepository.deleteById(id);
            log.debug("Deleted user with ID " + id + " from DB.");
            return "User with ID " + id + " deleted successfully.";
        } else {
            return "User with ID " + id + " is not present in DB.";
        }
    }

    public User login(LoginInfo loginInfo) {
        log.debug("Log in info : " + loginInfo);
        Optional<User> user = userRepository.findByEmail(loginInfo.getEmail());
        log.debug("Queried DB for user with email " + loginInfo.getEmail() + ". User " + (user.isPresent() ? "exists." : "does not exist."));
        if (user.isPresent()) {
            log.debug("User : " + user.get());
            if (BCrypt.checkpw(loginInfo.getPassword(), user.get().getPassword())) {
                log.debug("Password " + loginInfo.getPassword() + " matches users password. Log in successful.");
                return user.get();
            } else {
                log.debug("Password " + loginInfo.getPassword() + " does not match users password. Log in failed.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Passwords do not match!");
            }
        } else {
            log.debug("No user found with email " + loginInfo.getEmail() + ". Log in failed.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user found with email " + loginInfo.getEmail() + "!");
        }
    }

    public String resetPassword(PasswordReset passwordReset) {
        Optional<User> user = userRepository.findByEmail(passwordReset.getEmail());
        log.debug("Queried DB for user with email " + passwordReset.getEmail() + ". User " + (user.isPresent() ? "exists." : "does not exist."));

        if (user.isPresent()) {
            log.debug("User : " + user.get());
            User savedUser = user.get();
            String generatedPassword = generatePassword();
            log.debug("Generated random password for " + passwordReset.getEmail() + ".");
            log.debug("Hashing user password. Before hashing : " + generatedPassword);
            String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());
            savedUser.setPassword(hashedPassword);
            log.debug("Finished hashing user password. After hashing : " + hashedPassword);
            userRepository.save(savedUser);
            log.debug("Password reset successful for user " + savedUser);
            sendSimpleMessage(passwordReset.getEmail(), "HealthyCar Password Reset", generatedPassword);
            return "Password reset successful for user " + savedUser.getEmail() + ". Your new password has been sent to your email.";
        } else {
            log.debug("No user found with email " + passwordReset.getEmail() + ". Password reset failed.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user found with email " + passwordReset.getEmail() + "!");
        }
    }

    public String changePassword(PasswordChange passwordChange){
        Optional<User> user = userRepository.findById(new ObjectId(passwordChange.getUserId()));
        log.debug("Queried DB for user with ID " + passwordChange.getUserId() + ". User " + (user.isPresent() ? "exists." : "does not exist."));

        if(user.isPresent()){
            log.debug("User : " + user.get());
            User savedUser = user.get();
            if(BCrypt.checkpw(passwordChange.getCurrentPassword(), user.get().getPassword())){
                log.debug("Hashing user password. Before hashing : " + passwordChange.getNewPassword());
                String hashedPassword = BCrypt.hashpw(passwordChange.getNewPassword(), BCrypt.gensalt());
                savedUser.setPassword(hashedPassword);
                log.debug("Finished hashing user password. After hashing : " + hashedPassword);
                userRepository.save(savedUser);
                log.debug("Password change successful for user " + savedUser);
                return "Password change successful for user " + savedUser.getEmail() + ".";
            }else{
                log.debug("Password " + passwordChange.getCurrentPassword() + " does not match users password. Password change failed.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Passwords do not match!");
            }
        }else{
            log.debug("No user found with ID " + passwordChange.getUserId() + ". Password change failed.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user found with ID " + passwordChange.getUserId() + "!");
        }
    }

    public void sendSimpleMessage(String to, String subject, String generatedPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@healthycar.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        String message = String.format("Hello,\n\nYou requested to reset your HealthyCar password. Below you can find the new password for the account with email %s.\n\nYour new password : %s", to, generatedPassword);
        log.debug("Sending mail with message " + message + " to " + to + ".");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        log.debug("Sent mail with message " + message + " to " + to + ".");
    }

    public String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(4);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(4);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(3);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule specialCharsRule = new CharacterRule(specialChars);
        specialCharsRule.setNumberOfCharacters(2);

        return passwordGenerator.generatePassword(13, specialCharsRule, lowerCaseRule, upperCaseRule, digitRule);
    }
}

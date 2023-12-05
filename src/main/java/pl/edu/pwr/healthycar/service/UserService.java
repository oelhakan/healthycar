package pl.edu.pwr.healthycar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private JavaMailSender emailSender;

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
}

package pl.edu.pwr.healthycar.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"pl.edu.pwr.healthycar.persistence.repository"})
@RestController
public class HealthycarApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthycarApplication.class, args);
	}
}

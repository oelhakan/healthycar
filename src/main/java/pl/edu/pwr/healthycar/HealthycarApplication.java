package pl.edu.pwr.healthycar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableMongoRepositories
@RestController
public class HealthycarApplication {


	public static void main(String[] args) {
		SpringApplication.run(HealthycarApplication.class, args);
	}


}

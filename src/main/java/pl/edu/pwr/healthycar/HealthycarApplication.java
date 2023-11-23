package pl.edu.pwr.healthycar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.*;
import pl.edu.pwr.healthycar.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
@RestController
public class HealthycarApplication {


	public static void main(String[] args) {
		SpringApplication.run(HealthycarApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(ReportRepository repository, MongoTemplate template){
//		return args -> {
//			Report report = new Report(
//					"655f71b65b8e7f46d81eaf0d",
//					     45.04,
//					3404.28,
//					3.55,
//					5.31,
//					98
//			);
//
////			usingMongoTemplateAndQuery(repository, template, email, student);
//
////			repository.findStudentByEmail(email)
////					.ifPresentOrElse(s -> System.out.println(s + " Already exists"), () -> {
////						System.out.println("Inserting Student " + student);
////						repository.insert(student);
////					});
//
//			repository.insert(report);
//		};
//	}
//
//	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate template, String email, Student student) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("email").is(email));
//
//		List<Student> students = template.find(query, Student.class);
//
//		if(students.size() > 1){
//			throw new IllegalStateException("found many students with email " + email);
//		}
//
//		if(students.isEmpty()){
//			System.out.println("Inserting Student " + student);
//			repository.insert(student);
//		} else{
//			System.out.println(student + " Already exists");
//		}
//	}

}

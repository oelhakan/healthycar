package pl.edu.pwr.healthycar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pwr.healthycar.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}

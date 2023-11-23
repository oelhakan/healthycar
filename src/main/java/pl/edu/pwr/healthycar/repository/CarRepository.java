package pl.edu.pwr.healthycar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pwr.healthycar.model.Car;

public interface CarRepository extends MongoRepository<Car, String> {
}

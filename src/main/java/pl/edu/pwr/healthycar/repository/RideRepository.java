package pl.edu.pwr.healthycar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pwr.healthycar.model.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {
}

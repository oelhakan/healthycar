package pl.edu.pwr.healthycar.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.healthycar.persistence.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {

    Optional<Car> findById(ObjectId id);

    List<Car> findAllByOwnerId(String ownerId);
}

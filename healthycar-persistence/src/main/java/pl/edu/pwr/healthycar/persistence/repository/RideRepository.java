package pl.edu.pwr.healthycar.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.healthycar.api.model.Ride;


import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {

    Optional<Ride> findById(ObjectId id);

    List<Ride> findAllByCarId(String carId);

    List<Ride> findAllByUserId(String userId);
}

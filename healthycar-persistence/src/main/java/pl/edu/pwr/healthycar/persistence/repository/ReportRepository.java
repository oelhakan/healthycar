package pl.edu.pwr.healthycar.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.healthycar.persistence.model.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

    Optional<Report> findById(ObjectId id);

    List<Report> findAllByCarId(String carId);
}

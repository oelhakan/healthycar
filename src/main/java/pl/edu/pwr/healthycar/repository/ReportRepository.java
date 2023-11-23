package pl.edu.pwr.healthycar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pwr.healthycar.model.Report;

public interface ReportRepository extends MongoRepository<Report, String> {
}

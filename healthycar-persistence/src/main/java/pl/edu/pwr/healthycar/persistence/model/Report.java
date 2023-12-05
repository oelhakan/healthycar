package pl.edu.pwr.healthycar.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Report {

    @Id
    private String id;

    private String carId;
    private Double averageSpeed;
    private Double averageRpm;
    private Double averageFuel;
    private Double averageAirTemperature;
    private Integer totalDistance;
}

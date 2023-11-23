package pl.edu.pwr.healthycar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Report {
    @Id
    private String id;
    private String carId;
    private Double averageSpeed;
    private Double averageRpm;
    private Double averageFuel;
    private Double averageAirTemperature;
    private Integer totalDistance;

    public Report(String carId, Double averageSpeed, Double averageRpm, Double averageFuel, Double averageAirTemperature, Integer totalDistance) {
        this.carId = carId;
        this.averageSpeed = averageSpeed;
        this.averageRpm = averageRpm;
        this.averageFuel = averageFuel;
        this.averageAirTemperature = averageAirTemperature;
        this.totalDistance = totalDistance;
    }
}

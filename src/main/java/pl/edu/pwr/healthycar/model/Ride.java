package pl.edu.pwr.healthycar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Ride {
    @Id
    private String id;
    private String userId;
    private String carId;
    private LocalDateTime date;
    private List<Reading> readings;

    public Ride(String userId, String carId, LocalDateTime date, List<Reading> readings) {
        this.userId = userId;
        this.carId = carId;
        this.date = date;
        this.readings = readings;
    }
}

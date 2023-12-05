package pl.edu.pwr.healthycar.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@NoArgsConstructor
public class Ride {

    @Id
    private String id;

    private String userId;
    private String carId;
    private LocalDateTime date;
    private List<Reading> readings;
}

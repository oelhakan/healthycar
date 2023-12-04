package pl.edu.pwr.healthycar.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Car {

    @Id
    private String id;

    private String ownerId;
    private String name;

    @Indexed(unique = true)
    private String vin;

    private String make;
    private String model;
    private Integer year;
}

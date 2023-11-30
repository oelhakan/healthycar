package pl.edu.pwr.healthycar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
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

    public Car(String ownerId, String name, String vin, String make, String model, Integer year) {
        this.ownerId = ownerId;
        this.name = name;
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
    }
}

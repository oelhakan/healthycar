package pl.edu.pwr.healthycar.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reading {
    Integer speed;
    Integer rpm;
    Double fuelConsumption;
    Double airTemperature;
    Double engineTemperature;
}

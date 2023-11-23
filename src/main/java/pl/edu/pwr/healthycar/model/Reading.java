package pl.edu.pwr.healthycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reading {
    Integer speed;
    Integer rpm;
    Double fuelConsumption;
    Double airTemperature;
    Double engineTemperature;
}

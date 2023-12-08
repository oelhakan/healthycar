package pl.edu.pwr.healthycar.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reading {
    Integer speed;
    Integer rpm;
    Double fuelConsumption;
    Double airTemperature;
    Double engineTemperature;
}

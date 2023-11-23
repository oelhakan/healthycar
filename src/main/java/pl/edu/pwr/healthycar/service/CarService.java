package pl.edu.pwr.healthycar.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.repository.CarRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }
}

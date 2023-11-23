package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.service.CarService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("cars")
    public List<Car> getCars(){
        return carService.getAllCars();
    }
}

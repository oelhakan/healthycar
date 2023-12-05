package pl.edu.pwr.healthycar.api.service;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Car;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

public interface CarApi {

    @GetMapping(CARS)
    List<Car> getCars();

    @GetMapping(CARS_ID)
    Car getCar(@PathVariable String id);

    @GetMapping(CARS_OWNER_OWNERID)
    List<Car> getUserCars(@PathVariable String ownerId);

    @PostMapping(CARS_SAVE)
    Car upsertCar(@RequestBody Car car);

    @DeleteMapping(CARS_DELETE_ID)
    String deleteCar(@PathVariable String id);
}

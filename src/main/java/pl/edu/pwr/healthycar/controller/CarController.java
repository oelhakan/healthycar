package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.repository.CarRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/cars")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @GetMapping("/cars/{id}")
    public Car getCar(@PathVariable String id){
        return carRepository.findById(new ObjectId(id)).orElse(null);
    }

    @GetMapping("/cars/owner/{ownerId}")
    public List<Car> getUserCars(@PathVariable String ownerId){
        return carRepository.findAllByOwnerId(ownerId);
    }

    @PostMapping("/cars")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
    }

    @DeleteMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        carRepository.deleteById(id);
        return "Car " + id + " deleted successfully.";
    }

    //TODO - Get Car By VIN/Edit Car
}

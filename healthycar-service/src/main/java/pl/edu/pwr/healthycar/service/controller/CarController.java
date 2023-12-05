package pl.edu.pwr.healthycar.service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.persistence.model.Car;
import pl.edu.pwr.healthycar.service.service.CarService;

import java.util.List;

import static pl.edu.pwr.healthycar.service.utilities.Endpoints.*;

@RestController
@AllArgsConstructor
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(CARS)
    public List<Car> getCars(){
        log.debug(buildRequestLog(CARS));
        List<Car> cars = carService.getCars();
        log.debug(buildResponseLog(CARS) + cars);
        return cars;
    }

    @GetMapping(CARS_ID)
    public Car getCar(@PathVariable String id){
        log.debug(buildRequestLog(CARS_ID) + id);
        Car car = carService.getCar(id);
        log.debug(buildResponseLog(CARS_ID) + car);
        return car;
    }

    @GetMapping(CARS_OWNER_OWNERID)
    public List<Car> getUserCars(@PathVariable String ownerId){
        log.debug(buildRequestLog(CARS_OWNER_OWNERID) + ownerId);
        List<Car> userCars = carService.getUserCars(ownerId);
        log.debug(buildResponseLog(CARS_OWNER_OWNERID) + userCars);
        return userCars;
    }

    @PostMapping(CARS_SAVE)
    public Car upsertCar(@RequestBody Car car){
        log.debug(buildRequestLog(CARS_SAVE));
        Car updatedCar = carService.upsertCar(car);
        log.debug(buildResponseLog(CARS_SAVE) + updatedCar);
        return updatedCar;
    }

    @DeleteMapping(CARS_DELETE_ID)
    public String deleteCar(@PathVariable String id) {
        log.debug(buildRequestLog(CARS_DELETE_ID) + id);
        String deleteResult = carService.deleteCar(id);
        log.debug(buildResponseLog(CARS_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

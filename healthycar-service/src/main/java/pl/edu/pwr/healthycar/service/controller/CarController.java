package pl.edu.pwr.healthycar.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Car;
import pl.edu.pwr.healthycar.api.service.CarApi;
import pl.edu.pwr.healthycar.service.service.CarService;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

@RestController
@Slf4j
public class CarController implements CarApi {

    @Autowired
    private CarService carService;

    @Override
    public List<Car> getCars() {
        log.debug(buildRequestLog(CARS));
        List<Car> cars = carService.getAll();
        log.debug(buildResponseLog(CARS) + cars);
        return cars;
    }

    @Override
    public Car getCar(String id) {
        log.debug(buildRequestLog(CARS_ID) + id);
        Car car = carService.getOne(id);
        log.debug(buildResponseLog(CARS_ID) + car);
        return car;
    }

    @Override
    public List<Car> getUserCars(String ownerId) {
        log.debug(buildRequestLog(CARS_OWNER_OWNERID) + ownerId);
        List<Car> userCars = carService.getUserCars(ownerId);
        log.debug(buildResponseLog(CARS_OWNER_OWNERID) + userCars);
        return userCars;
    }

    @Override
    public Car upsertCar(Car car) {
        log.debug(buildRequestLog(CARS_SAVE));
        Car updatedCar = carService.upsert(car);
        log.debug(buildResponseLog(CARS_SAVE) + updatedCar);
        return updatedCar;
    }

    @Override
    public String deleteCar(String id) {
        log.debug(buildRequestLog(CARS_DELETE_ID) + id);
        String deleteResult = carService.delete(id);
        log.debug(buildResponseLog(CARS_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

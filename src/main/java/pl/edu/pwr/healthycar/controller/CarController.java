package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.repository.CarRepository;
import pl.edu.pwr.healthycar.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class CarController {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @GetMapping("/cars")
    public List<Car> getCars(){
        log.debug("REQ => /cars");
        List<Car> cars = carRepository.findAll();
        log.debug(String.format("Queried DB for cars. Found %d %s", cars.size(), cars.size() == 1 ? "car." : "cars."));
        log.debug("RES => " + cars);
        return cars;
    }

    @GetMapping("/cars/{id}")
    public Car getCar(@PathVariable String id){
        log.debug("REQ => /cars/" + id);
        Optional<Car> car = carRepository.findById(new ObjectId(id));
        log.debug("Queried DB for car with ID " + id + ". Car " + (car.isPresent() ? "exists." : "does not exist."));
        log.debug("RES => " + car.orElse(null));
        return car.orElse(null);
    }

    @GetMapping("/cars/owner/{ownerId}")
    public List<Car> getUserCars(@PathVariable String ownerId){
        log.debug("REQ => /cars/owner/" + ownerId);
        List<Car> userCars = carRepository.findAllByOwnerId(ownerId);
        log.debug(String.format("Queried DB for cars with owner ID %s. Found %d %s", ownerId, userCars.size(), userCars.size() == 1 ? "car." : "cars."));
        log.debug("RES => " + userCars);
        return userCars;
    }

    @PostMapping("/cars/save")
    public Car upsertCar(@RequestBody Car car){
        log.debug("REQ => /cars/add");
        log.debug("Adding car with request body " + car);
        User user = userRepository.findById(new ObjectId(car.getOwnerId())).get();
        log.debug("Queried DB for owner of car with ID " + car.getOwnerId() + ". Owner : " + user);
        if(user.getIsAdmin() || user.getIsFO()){
            log.debug("User is either admin or fleet owner. Skipped car count check.");
            Car saved = saveCar(car);
            incrementCarCount(user);
            return saved;
        }else{
            log.debug("User is not admin or fleet owner. Checking the car count.");
            if(user.getCarCount() < 2){
                log.debug("Car count does not exceed 2. Adding new car with owner ID " + car.getOwnerId());
                Car saved = saveCar(car);
                incrementCarCount(user);
                return saved;
            }else{
                log.debug("Car count exceeds 2. Throwing ResponseStatusException.");
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Car Count Limit Exceeded!");
            }
        }
    }

    @DeleteMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        log.debug("REQ => /cars/delete/" + id);
        Optional<Car> car = carRepository.findById(new ObjectId(id));
        log.debug("Queried DB for car with ID " + id + ". Car " + (car.isPresent() ? "exists." : "does not exist."));

        if(car.isPresent()){
            User user  = userRepository.findById(new ObjectId(car.get().getOwnerId())).get();
            log.debug("Queried DB for owner of car with ID " + car.get().getOwnerId() + ". Owner : " + user);
            log.debug("Decrementing car count of user with ID " + user.getId());
            log.debug("New car count of user : " + (user.getCarCount() - 1));
            userRepository.save(user);
            carRepository.deleteById(id);
            log.debug("Deleted car with ID " + id + " from DB.");
            log.debug("RES => Car with ID " + id + " deleted successfully.");
            return "Car with ID " + id + " deleted successfully.";
        }else{
            log.debug("RES => Car with ID " + id + " is not present in DB.");
            return "Car with ID " + id + " is not present in DB.";
        }
    }

    private Car saveCar(Car car){
        try{
            Optional<Car> dbCar = carRepository.findById(car.getId());
            if(dbCar.isPresent()){
                log.debug("Car with ID " + car.getId() + " already exists. Updated the record.");
            }else{
                log.debug("Car with ID " + car.getId() + " does not exist. Created new record.");
            }
        }catch(IllegalArgumentException ex){
            log.debug("Car ID was not provided in request body. Created new record with auto generated ID.");
        }
        Car savedCar = carRepository.save(car);

        log.debug("RES => " + savedCar);
        return savedCar;
    }

    private void incrementCarCount(User user){
        log.debug("Incrementing car count of user with ID " + user.getId());
        log.debug("New car count of user : " + (user.getCarCount() + 1));
        user.setCarCount(user.getCarCount() + 1);
        userRepository.save(user);
    }
}

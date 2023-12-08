package pl.edu.pwr.healthycar.service.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.api.model.Car;
import pl.edu.pwr.healthycar.api.model.User;
import pl.edu.pwr.healthycar.persistence.repository.CarRepository;
import pl.edu.pwr.healthycar.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Car> getCars() {
        List<Car> cars = carRepository.findAll();
        log.debug(String.format("Queried DB for cars. Found %d reports.", cars.size()));
        return cars;
    }

    public Car getCar(String id) {
        Optional<Car> car = carRepository.findById(new ObjectId(id));
        log.debug("Queried DB for car with ID " + id + ". Car " + (car.isPresent() ? "exists." : "does not exist."));
        return car.orElse(null);
    }

    public List<Car> getUserCars(String ownerId) {
        List<Car> userCars = carRepository.findAllByOwnerId(ownerId);
        log.debug(String.format("Queried DB for cars with owner ID %s. Found %d cars.", ownerId, userCars.size()));
        return userCars;
    }

    public Car upsertCar(Car car) {
        log.debug("Adding car with request body " + car);
        User user = userRepository.findById(new ObjectId(car.getOwnerId())).get();
        log.debug("Queried DB for owner of car with ID " + car.getOwnerId() + ". Owner : " + user);
        if (user.getIsAdmin() || user.getIsFO()) {
            log.debug("User is either admin or fleet owner. Skipped car count check.");
            Car saved = saveCar(car);
            incrementCarCount(user);
            return saved;
        } else {
            log.debug("User is not admin or fleet owner. Checking the car count.");
            if (user.getCarCount() < 2) {
                log.debug("Car count does not exceed 2. Adding new car with owner ID " + car.getOwnerId());
                Car saved = saveCar(car);
                incrementCarCount(user);
                return saved;
            } else {
                log.debug("Car count exceeds 2. Throwing ResponseStatusException.");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Car Count Limit Exceeded!");
            }
        }
    }

    private Car saveCar(Car car) {
        try {
            Optional<Car> dbCar = carRepository.findById(car.getId());
            if (dbCar.isPresent()) {
                log.debug("Car with ID " + car.getId() + " already exists. Updated the record.");
            } else {
                log.debug("Car with ID " + car.getId() + " does not exist. Created new record.");
            }
        } catch (IllegalArgumentException ex) {
            log.debug("Car ID was not provided in request body. Created new record with auto generated ID.");
        }

        return carRepository.save(car);
    }

    private void incrementCarCount(User user) {
        log.debug("Incrementing car count of user with ID " + user.getId());
        log.debug("New car count of user : " + (user.getCarCount() + 1));
        user.setCarCount(user.getCarCount() + 1);
        userRepository.save(user);
    }

    public String deleteCar(String id) {
        Optional<Car> car = carRepository.findById(new ObjectId(id));
        log.debug("Queried DB for car with ID " + id + ". Car " + (car.isPresent() ? "exists." : "does not exist."));

        if (car.isPresent()) {
            User user = userRepository.findById(new ObjectId(car.get().getOwnerId())).get();
            log.debug("Queried DB for owner of car with ID " + car.get().getOwnerId() + ". Owner : " + user);
            log.debug("Decrementing car count of user with ID " + user.getId());
            log.debug("New car count of user : " + (user.getCarCount() - 1));
            user.setCarCount(user.getCarCount() - 1);
            userRepository.save(user);
            carRepository.deleteById(id);
            log.debug("Deleted car with ID " + id + " from DB.");
            return "Car with ID " + id + " deleted successfully.";
        } else {
            return "Car with ID " + id + " is not present in DB.";
        }
    }
}

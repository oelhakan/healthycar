package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.model.User;
import pl.edu.pwr.healthycar.repository.CarRepository;
import pl.edu.pwr.healthycar.repository.UserRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

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

    @PostMapping("/cars/add")
    public Car addCar(@RequestBody Car car){
        User user = userRepository.findById(new ObjectId(car.getOwnerId())).get();
        if(user.getIsAdmin() || user.getIsFO()){
            Car saved = carRepository.save(car);
            user.setCarCount(user.getCarCount() + 1);
            userRepository.save(user);
            return saved;
        }else{
            if(user.getCarCount() < 2){
                Car saved = carRepository.save(car);
                user.setCarCount(user.getCarCount() + 1);
                userRepository.save(user);
                return saved;
            }else{
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Car Count Limit Exceeded!");
            }
        }
    }

    @DeleteMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        Car car = carRepository.findById(new ObjectId(id)).get();
        User user  = userRepository.findById(new ObjectId(car.getOwnerId())).get();
        user.setCarCount(user.getCarCount() - 1);
        userRepository.save(user);
        carRepository.deleteById(id);
        return "Car " + id + " deleted successfully.";
    }
}

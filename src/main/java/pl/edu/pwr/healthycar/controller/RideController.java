package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Ride;
import pl.edu.pwr.healthycar.repository.RideRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class RideController {

    private final RideRepository rideRepository;

    @GetMapping("/rides")
    public List<Ride> getRides() {
        return rideRepository.findAll();
    }

    @GetMapping("/rides/{id}")
    public Ride getRide(@PathVariable String id) {
        return rideRepository.findById(new ObjectId(id)).orElse(null);
    }

    @GetMapping("/rides/user/{userId}")
    public List<Ride> getUserRides(@PathVariable String userId) {
        return rideRepository.findAllByUserId(userId);
    }

    @GetMapping("/rides/car/{carId}")
    public List<Ride> getCarRides(@PathVariable String carId) {
        return rideRepository.findAllByCarId(carId);
    }

    @PostMapping("/rides/add")
    public Ride addRide(@RequestBody Ride ride) {
        return rideRepository.save(ride);
    }

    @DeleteMapping("/rides/delete/{id}")
    public String deleteRide(@PathVariable String id) {
        rideRepository.deleteById(id);
        return "Ride " + id + " deleted successfully.";
    }
}

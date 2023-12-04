package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Ride;
import pl.edu.pwr.healthycar.repository.RideRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class RideController {

    private final RideRepository rideRepository;

    @GetMapping("/rides")
    public List<Ride> getRides() {
        log.debug("REQ => /rides");
        List<Ride> rides = rideRepository.findAll();
        log.debug(String.format("Queried DB for rides. Found %d %s", rides.size(), rides.size() == 1 ? "ride." : "rides."));
        log.debug("RES => " + rides);
        return rides;
    }

    @GetMapping("/rides/{id}")
    public Ride getRide(@PathVariable String id) {
        log.debug("REQ => /rides/" + id);
        Optional<Ride> ride = rideRepository.findById(new ObjectId(id));
        log.debug("Queried DB for ride with ID " + id + ". Ride " + (ride.isPresent() ? "exists." : "does not exist."));
        log.debug("RES => " + ride.orElse(null));
        return ride.orElse(null);
    }

    @GetMapping("/rides/user/{userId}")
    public List<Ride> getUserRides(@PathVariable String userId) {
        log.debug("REQ => /rides/user/" + userId);
        List<Ride> userRides = rideRepository.findAllByUserId(userId);
        log.debug(String.format("Queried DB for rides with user ID %s. Found %d %s", userId, userRides.size(), userRides.size() == 1 ? "ride." : "rides."));
        log.debug("RES => " + userRides);
        return userRides;
    }

    @GetMapping("/rides/latest/{carId}")
    public Ride getCarLatestRide(@PathVariable String carId) {
        log.debug("REQ => /rides/latest/" + carId);
        List<Ride> carRides = rideRepository.findAllByCarId(carId);
        log.debug(String.format("Queried DB for rides with car ID %s. Found %d %s", carId, carRides.size(), carRides.size() == 1 ? "ride." : "rides."));
        log.debug("Sorting the rides in descending order by date. Rides before : " + carRides);
        carRides.sort(Comparator.comparing(Ride::getDate).reversed());
        log.debug("Finished sorting the rides in descending order by date. Rides after : " + carRides);
        log.debug("RES => " + (carRides.size() > 0 ? carRides.get(0) : null));
        return carRides.size() > 0 ? carRides.get(0) : null;
    }

    @PostMapping("/rides/add")
    public Ride addRide(@RequestBody Ride ride) {
        log.debug("REQ => /rides/add");
        log.debug("Adding ride with request body " + ride);

        try{
            Optional<Ride> dbRide = rideRepository.findById(ride.getId());
            if(dbRide.isPresent()){
                log.debug("Ride with ID " + ride.getId() + " already exists. Updated the record.");
            }else{
                log.debug("Ride with ID " + ride.getId() + " does not exist. Created new record.");
            }
        }catch(IllegalArgumentException ex){
            log.debug("Ride ID was not provided in request body. Created new record with auto generated ID.");
        }

        Ride savedRide = rideRepository.save(ride);

        log.debug("RES => " + savedRide);
        return savedRide;
    }

    @DeleteMapping("/rides/delete/{id}")
    public String deleteRide(@PathVariable String id) {
        log.debug("REQ => /rides/delete/" + id);
        Optional<Ride> ride = rideRepository.findById(new ObjectId(id));
        log.debug("Queried DB for ride with ID " + id + ". Ride " + (ride.isPresent() ? "exists." : "does not exist."));
        if(ride.isPresent()){
            rideRepository.deleteById(id);
            log.debug("Deleted ride with ID " + id + " from DB.");
            log.debug("RES => Ride with ID " + id + " deleted successfully.");
            return "Ride with ID " + id + " deleted successfully.";
        }else{
            log.debug("RES => Ride with ID " + id + " is not present in DB.");
            return "Ride with ID " + id + " is not present in DB.";
        }
    }
}

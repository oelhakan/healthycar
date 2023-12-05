package pl.edu.pwr.healthycar.service.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.healthycar.persistence.model.Ride;
import pl.edu.pwr.healthycar.persistence.repository.RideRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public List<Ride> getRides() {
        List<Ride> rides = rideRepository.findAll();
        log.debug(String.format("Queried DB for rides. Found %d %s", rides.size(), rides.size() == 1 ? "ride." : "rides."));
        return rides;
    }

    public Ride getRide(String id) {
        Optional<Ride> ride = rideRepository.findById(new ObjectId(id));
        log.debug("Queried DB for ride with ID " + id + ". Ride " + (ride.isPresent() ? "exists." : "does not exist."));
        return ride.orElse(null);
    }

    public List<Ride> getUserRides(String userId) {
        List<Ride> userRides = rideRepository.findAllByUserId(userId);
        log.debug(String.format("Queried DB for rides with user ID %s. Found %d %s", userId, userRides.size(), userRides.size() == 1 ? "ride." : "rides."));
        return userRides;
    }

    public Ride getLatestCarRide(String carId) {
        List<Ride> carRides = rideRepository.findAllByCarId(carId);
        log.debug(String.format("Queried DB for rides with car ID %s. Found %d %s", carId, carRides.size(), carRides.size() == 1 ? "ride." : "rides."));
        log.debug("Sorting the rides in descending order by date. Rides before : " + carRides);
        carRides.sort(Comparator.comparing(Ride::getDate).reversed());
        log.debug("Finished sorting the rides in descending order by date. Rides after : " + carRides);
        Ride latestRide = (carRides.size() > 0 ? carRides.get(0) : null);
        log.debug("RES => " + latestRide);
        return latestRide;
    }

    public Ride upsertRide(Ride ride) {
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

        return rideRepository.save(ride);
    }

    public String deleteRide(String id) {
        Optional<Ride> ride = rideRepository.findById(new ObjectId(id));
        log.debug("Queried DB for ride with ID " + id + ". Ride " + (ride.isPresent() ? "exists." : "does not exist."));
        if(ride.isPresent()){
            rideRepository.deleteById(id);
            log.debug("Deleted ride with ID " + id + " from DB.");
            return "Ride with ID " + id + " deleted successfully.";
        }else{
            return "Ride with ID " + id + " is not present in DB.";
        }
    }
}

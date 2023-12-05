package pl.edu.pwr.healthycar.service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.persistence.model.Ride;
import pl.edu.pwr.healthycar.service.service.RideService;

import java.util.List;

import static pl.edu.pwr.healthycar.service.utilities.Endpoints.*;

@RestController
@AllArgsConstructor
@Slf4j
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping(RIDES)
    public List<Ride> getRides() {
        log.debug(buildRequestLog(RIDES));
        List<Ride> rides = rideService.getRides();
        log.debug(buildResponseLog(RIDES) + rides);
        return rides;
    }

    @GetMapping(RIDES_ID)
    public Ride getRide(@PathVariable String id) {
        log.debug(buildRequestLog(RIDES_ID) + id);
        Ride ride = rideService.getRide(id);
        log.debug(buildResponseLog(RIDES_ID) + ride);
        return ride;
    }

    @GetMapping(RIDES_USER_USERID)
    public List<Ride> getUserRides(@PathVariable String userId) {
        log.debug(buildRequestLog(RIDES_USER_USERID) + userId);
        List<Ride> userRides = rideService.getUserRides(userId);
        log.debug(buildResponseLog(RIDES_USER_USERID) + userRides);
        return userRides;
    }

    @GetMapping(RIDES_LATEST_CARID)
    public Ride getLatestCarRide(@PathVariable String carId) {
        log.debug(buildRequestLog(RIDES_LATEST_CARID) + carId);
        Ride latestCarRide = rideService.getLatestCarRide(carId);
        log.debug(buildResponseLog(RIDES_LATEST_CARID) + latestCarRide);
        return latestCarRide;
    }

    @PostMapping(RIDES_SAVE)
    public Ride upsertRide(@RequestBody Ride ride) {
        log.debug(buildRequestLog(RIDES_SAVE));
        Ride savedRide = rideService.upsertRide(ride);
        log.debug(buildResponseLog(RIDES_SAVE) + savedRide);
        return savedRide;
    }

    @DeleteMapping(RIDES_DELETE_ID)
    public String deleteRide(@PathVariable String id) {
        log.debug(buildRequestLog(RIDES_DELETE_ID) + id);
        String deleteResult = rideService.deleteRide(id);
        log.debug(buildResponseLog(RIDES_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

package pl.edu.pwr.healthycar.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Ride;
import pl.edu.pwr.healthycar.api.service.RideApi;
import pl.edu.pwr.healthycar.service.service.RideService;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

@RestController
@Slf4j
public class RideController implements RideApi {

    @Autowired
    private RideService rideService;

    @Override
    public List<Ride> getRides() {
        log.debug(buildRequestLog(RIDES));
        List<Ride> rides = rideService.getRides();
        log.debug(buildResponseLog(RIDES) + rides);
        return rides;
    }

    @Override
    public Ride getRide(String id) {
        log.debug(buildRequestLog(RIDES_ID) + id);
        Ride ride = rideService.getRide(id);
        log.debug(buildResponseLog(RIDES_ID) + ride);
        return ride;
    }

    @Override
    public List<Ride> getUserRides(String userId) {
        log.debug(buildRequestLog(RIDES_USER_USERID) + userId);
        List<Ride> userRides = rideService.getUserRides(userId);
        log.debug(buildResponseLog(RIDES_USER_USERID) + userRides);
        return userRides;
    }

    @Override
    public Ride getLatestCarRide(String carId) {
        log.debug(buildRequestLog(RIDES_LATEST_CARID) + carId);
        Ride latestCarRide = rideService.getLatestCarRide(carId);
        log.debug(buildResponseLog(RIDES_LATEST_CARID) + latestCarRide);
        return latestCarRide;
    }

    @Override
    public Ride upsertRide(Ride ride) {
        log.debug(buildRequestLog(RIDES_SAVE));
        Ride savedRide = rideService.upsertRide(ride);
        log.debug(buildResponseLog(RIDES_SAVE) + savedRide);
        return savedRide;
    }

    @Override
    public String deleteRide(String id) {
        log.debug(buildRequestLog(RIDES_DELETE_ID) + id);
        String deleteResult = rideService.deleteRide(id);
        log.debug(buildResponseLog(RIDES_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

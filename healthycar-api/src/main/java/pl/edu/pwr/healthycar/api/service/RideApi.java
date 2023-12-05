package pl.edu.pwr.healthycar.api.service;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Ride;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

public interface RideApi {

    @GetMapping(RIDES)
    List<Ride> getRides();

    @GetMapping(RIDES_ID)
    Ride getRide(@PathVariable String id);

    @GetMapping(RIDES_USER_USERID)
    List<Ride> getUserRides(@PathVariable String userId);

    @GetMapping(RIDES_LATEST_CARID)
    Ride getLatestCarRide(@PathVariable String carId);

    @PostMapping(RIDES_SAVE)
    Ride upsertRide(@RequestBody Ride ride);

    @DeleteMapping(RIDES_DELETE_ID)
    String deleteRide(@PathVariable String id);
}

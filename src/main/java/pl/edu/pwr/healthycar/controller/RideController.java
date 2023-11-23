package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.healthycar.model.Ride;
import pl.edu.pwr.healthycar.service.RideService;

import java.util.List;

@RestController
@AllArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping("rides")
    public List<Ride> getRides(){
        return rideService.getAllRides();
    }
}

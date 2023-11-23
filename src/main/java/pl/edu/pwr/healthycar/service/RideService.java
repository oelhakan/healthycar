package pl.edu.pwr.healthycar.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pwr.healthycar.model.Ride;
import pl.edu.pwr.healthycar.repository.RideRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class RideService {

    private final RideRepository rideRepository;

    public List<Ride> getAllRides(){
        return rideRepository.findAll();
    }
}

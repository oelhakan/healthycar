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
//        [
//        {
//            "id": "655f757f7049730b46a8642e",
//                "userId": "655f6c358cb277402a49f485",
//                "carId": "655f71b65b8e7f46d81eaf0d",
//                "date": "2023-11-23T16:53:35.434",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//        ]
//        },
//        {
//            "id": "6568a0d76a5daf1f477e24b7",
//                "userId": "656893717f3e3013acdff660",
//                "carId": "65689579debde221a7ecf12b",
//                "date": "2014-01-01T00:00:00",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//        ]
//        }
//]
    }

    @GetMapping("/rides/{id}")
    public Ride getRide(@PathVariable String id) {
        return rideRepository.findById(new ObjectId(id)).orElse(null);
//        {
//            "id": "655f757f7049730b46a8642e",
//                "userId": "655f6c358cb277402a49f485",
//                "carId": "655f71b65b8e7f46d81eaf0d",
//                "date": "2023-11-23T16:53:35.434",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//    ]
//        }
    }

    @GetMapping("/rides/user/{userId}")
    public List<Ride> getUserRides(@PathVariable String userId) {
        return rideRepository.findAllByUserId(userId);
//        [
//        {
//            "id": "6568a0d76a5daf1f477e24b7",
//                "userId": "656893717f3e3013acdff660",
//                "carId": "65689579debde221a7ecf12b",
//                "date": "2014-01-01T00:00:00",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//        ]
//        }
//]
    }

    @GetMapping("/rides/car/{carId}")
    public List<Ride> getCarRides(@PathVariable String carId) {
        return rideRepository.findAllByCarId(carId);
//        [
//        {
//            "id": "6568a0d76a5daf1f477e24b7",
//                "userId": "656893717f3e3013acdff660",
//                "carId": "65689579debde221a7ecf12b",
//                "date": "2014-01-01T00:00:00",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//        ]
//        }
//]
    }

    @PostMapping("/rides")
    public Ride addRide(@RequestBody Ride ride) {
        return rideRepository.save(ride);
//        {
//            "id": "6568a0d76a5daf1f477e24b7",
//                "userId": "656893717f3e3013acdff660",
//                "carId": "65689579debde221a7ecf12b",
//                "date": "2014-01-01T00:00:00",
//                "readings": [
//            {
//                "speed": 25,
//                    "rpm": 2581,
//                    "fuelConsumption": 4.42,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 22,
//                    "rpm": 3469,
//                    "fuelConsumption": 4.23,
//                    "airTemperature": 9.8,
//                    "engineTemperature": 91.0
//            },
//            {
//                "speed": 26,
//                    "rpm": 1536,
//                    "fuelConsumption": 3.78,
//                    "airTemperature": 7.9,
//                    "engineTemperature": 89.2
//            },
//            {
//                "speed": 36,
//                    "rpm": 2308,
//                    "fuelConsumption": 5.37,
//                    "airTemperature": 11.5,
//                    "engineTemperature": 80.2
//            },
//            {
//                "speed": 17,
//                    "rpm": 3207,
//                    "fuelConsumption": 5.52,
//                    "airTemperature": 9.7,
//                    "engineTemperature": 89.6
//            }
//    ]
//        }
    }

    @DeleteMapping("/rides/delete/{id}")
    public String deleteRide(@PathVariable String id) {
        rideRepository.deleteById(id);
        return "Ride " + id + " deleted successfully.";
        //Ride 655f6c358cb277402a49f485 deleted successfully.
    }
}

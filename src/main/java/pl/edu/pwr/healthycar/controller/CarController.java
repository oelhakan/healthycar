package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Car;
import pl.edu.pwr.healthycar.repository.CarRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/cars")
    public List<Car> getCars(){
        return carRepository.findAll();
//        [
//        {
//            "id": "655f71b65b8e7f46d81eaf0d",
//                "ownerId": "655f6c358cb277402a49f485",
//                "name": "Asfalt",
//                "vin": "28702970936820932",
//                "make": "Opel",
//                "model": "Astra",
//                "year": 2018
//        },
//        {
//            "id": "65689579debde221a7ecf12b",
//                "ownerId": "656893717f3e3013acdff660",
//                "name": "Giant",
//                "vin": "287018274631820932",
//                "make": "Ford",
//                "model": "Focus",
//                "year": 2015
//        }
//]
    }

    @GetMapping("/cars/{id}")
    public Car getCar(@PathVariable String id){
        return carRepository.findById(new ObjectId(id)).orElse(null);
//        {
//            "id": "655f71b65b8e7f46d81eaf0d",
//                "ownerId": "655f6c358cb277402a49f485",
//                "name": "Asfalt",
//                "vin": "28702970936820932",
//                "make": "Opel",
//                "model": "Astra",
//                "year": 2018
//        }
    }

    @GetMapping("/cars/owner/{ownerId}")
    public List<Car> getUserCars(@PathVariable String ownerId){
        return carRepository.findAllByOwnerId(ownerId);
//        [
//        {
//            "id": "65689579debde221a7ecf12b",
//                "ownerId": "656893717f3e3013acdff660",
//                "name": "Giant",
//                "vin": "287018274631820932",
//                "make": "Ford",
//                "model": "Focus",
//                "year": 2015
//        }
//]
    }

    @PostMapping("/cars")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);
//        {
//            "id": "65689579debde221a7ecf12b",
//                "ownerId": "656893717f3e3013acdff660",
//                "name": "Giant",
//                "vin": "287018274631820932",
//                "make": "Ford",
//                "model": "Focus",
//                "year": 2015
//        }
    }

    @DeleteMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        carRepository.deleteById(id);
        return "Car " + id + " deleted successfully.";
        //Car 655f6c358cb277402a49f485 deleted successfully.
    }

    //TODO - Get Car By VIN/Edit Car
}

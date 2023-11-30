package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Report;
import pl.edu.pwr.healthycar.repository.ReportRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;

    @GetMapping("/reports")
    public List<Report> getReports() {
        return reportRepository.findAll();
//        [
//        {
//            "id": "655f770dfb5f68211eae27d2",
//                "carId": "655f71b65b8e7f46d81eaf0d",
//                "averageSpeed": 45.04,
//                "averageRpm": 3404.28,
//                "averageFuel": 3.55,
//                "averageAirTemperature": 5.31,
//                "totalDistance": 98
//        },
//        {
//            "id": "65689b42444cbf0c24cabcf5",
//                "carId": "65689579debde221a7ecf12b",
//                "averageSpeed": 45.04,
//                "averageRpm": 3404.28,
//                "averageFuel": 3.55,
//                "averageAirTemperature": 5.31,
//                "totalDistance": 98
//        }
//]
    }

    @GetMapping("/reports/{id}")
    public Report getReport(@PathVariable String id) {
        return reportRepository.findById(new ObjectId(id)).orElse(null);
//        {
//            "id": "655f770dfb5f68211eae27d2",
//                "carId": "655f71b65b8e7f46d81eaf0d",
//                "averageSpeed": 45.04,
//                "averageRpm": 3404.28,
//                "averageFuel": 3.55,
//                "averageAirTemperature": 5.31,
//                "totalDistance": 98
//        }
    }

    @GetMapping("/reports/car/{carId}")
    public List<Report> getCarReports(@PathVariable String carId) {
        return reportRepository.findAllByCarId(carId);
//        [
//        {
//            "id": "655f770dfb5f68211eae27d2",
//                "carId": "655f71b65b8e7f46d81eaf0d",
//                "averageSpeed": 45.04,
//                "averageRpm": 3404.28,
//                "averageFuel": 3.55,
//                "averageAirTemperature": 5.31,
//                "totalDistance": 98
//        }
//]
    }

    @PostMapping("/reports")
    public Report addReport(@RequestBody Report report) {
        return reportRepository.save(report);
//        {
//            "id": "65689b42444cbf0c24cabcf5",
//                "carId": "65689579debde221a7ecf12b",
//                "averageSpeed": 45.04,
//                "averageRpm": 3404.28,
//                "averageFuel": 3.55,
//                "averageAirTemperature": 5.31,
//                "totalDistance": 98
//        }
    }

    @DeleteMapping("/reports/delete/{id}")
    public String deleteReport(@PathVariable String id) {
        reportRepository.deleteById(id);
        return "Report " + id + " deleted successfully.";
        //Report 655f6c358cb277402a49f485 deleted successfully.
    }
}

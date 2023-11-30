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
    }

    @GetMapping("/reports/{id}")
    public Report getReport(@PathVariable String id) {
        return reportRepository.findById(new ObjectId(id)).orElse(null);
    }

    @GetMapping("/reports/car/{carId}")
    public List<Report> getCarReports(@PathVariable String carId) {
        return reportRepository.findAllByCarId(carId);
    }

    @PostMapping("/reports")
    public Report addReport(@RequestBody Report report) {
        return reportRepository.save(report);
    }

    @DeleteMapping("/reports/delete/{id}")
    public String deleteReport(@PathVariable String id) {
        reportRepository.deleteById(id);
        return "Report " + id + " deleted successfully.";
    }
}

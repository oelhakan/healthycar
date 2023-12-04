package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.model.Report;
import pl.edu.pwr.healthycar.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class ReportController {

    private final ReportRepository reportRepository;

    @GetMapping("/reports")
    public List<Report> getReports() {
        log.debug("REQ => /reports");
        List<Report> reports = reportRepository.findAll();
        log.debug(String.format("Queried DB for reports. Found %d %s", reports.size(), reports.size() == 1 ? " report." : " reports."));
        log.debug("RES => " + reports);
        return reportRepository.findAll();
    }

    @GetMapping("/reports/{id}")
    public Report getReport(@PathVariable String id) {
        log.debug("REQ => /reports/" + id);
        Optional<Report> report = reportRepository.findById(new ObjectId(id));
        log.debug("Queried DB for report with ID " + id + ". Report " + (report.isPresent() ? "exists." : "does not exist."));
        log.debug("RES => " + report.orElse(null));
        return report.orElse(null);
    }

    @GetMapping("/reports/car/{carId}")
    public List<Report> getCarReports(@PathVariable String carId) {
        log.debug("REQ => /reports/car/" + carId);
        List<Report> carReports = reportRepository.findAllByCarId(carId);
        log.debug(String.format("Queried DB for reports with car ID %s. Found %d %s", carId, carReports.size(), carReports.size() == 1 ? " report." : " reports."));
        log.debug("RES => " + carReports);
        return reportRepository.findAllByCarId(carId);
    }

    @PostMapping("/reports/add")
    public Report addReport(@RequestBody Report report) {
        log.debug("REQ => /reports/add");
        log.debug("Adding report with request body " + report);

        try{
            Optional<Report> dbReport = reportRepository.findById(report.getId());
            if(dbReport.isPresent()){
                log.debug("Report with ID " + report.getId() + " already exists. Updated the record.");
            }else{
                log.debug("Report with ID " + report.getId() + " does not exist. Created new record.");
            }
        }catch(IllegalArgumentException ex){
            log.debug("Report ID was not provided in request body. Created new record with auto generated ID.");
        }

        Report savedReport = reportRepository.save(report);

        log.debug("RES => " + savedReport);
        return savedReport;
    }

    @DeleteMapping("/reports/delete/{id}")
    public String deleteReport(@PathVariable String id) {
        log.debug("REQ => /reports/delete/" + id);
        Optional<Report> report = reportRepository.findById(new ObjectId(id));
        log.debug("Queried DB for report with ID " + id + ". Report " + (report.isPresent() ? "exists." : "does not exist."));
        if(report.isPresent()){
            reportRepository.deleteById(id);
            log.debug("Deleted report with ID " + id + " from DB.");
            log.debug("RES => Report with ID " + id + " deleted successfully.");
            return "Report with ID " + id + " deleted successfully.";
        }else{
            log.debug("RES => Report with ID " + id + " is not present in DB.");
            return "Report with ID " + id + " is not present in DB.";
        }
    }
}

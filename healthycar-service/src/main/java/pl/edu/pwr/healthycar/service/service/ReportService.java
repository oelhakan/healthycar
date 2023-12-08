package pl.edu.pwr.healthycar.service.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.healthycar.api.model.Report;
import pl.edu.pwr.healthycar.persistence.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getReports() {
        List<Report> reports = reportRepository.findAll();
        log.debug(String.format("Queried DB for reports. Found %d reports.", reports.size()));
        return reports;
    }

    public Report getReport(String id) {
        Optional<Report> report = reportRepository.findById(new ObjectId(id));
        log.debug("Queried DB for report with ID " + id + ". Report " + (report.isPresent() ? "exists." : "does not exist."));
        return report.orElse(null);
    }

    public List<Report> getCarReports(String carId) {
        List<Report> carReports = reportRepository.findAllByCarId(carId);
        log.debug(String.format("Queried DB for reports with car ID %s. Found %d reports.", carId, carReports.size()));
        return carReports;
    }

    public Report upsertReport(Report report) {
        log.debug("Adding report with request body " + report);

        try {
            Optional<Report> dbReport = reportRepository.findById(report.getId());
            if (dbReport.isPresent()) {
                log.debug("Report with ID " + report.getId() + " already exists. Updated the record.");
            } else {
                log.debug("Report with ID " + report.getId() + " does not exist. Created new record.");
            }
        } catch (IllegalArgumentException ex) {
            log.debug("Report ID was not provided in request body. Created new record with auto generated ID.");
        }

        return reportRepository.save(report);
    }

    public String deleteReport(String id) {
        Optional<Report> report = reportRepository.findById(new ObjectId(id));
        log.debug("Queried DB for report with ID " + id + ". Report " + (report.isPresent() ? "exists." : "does not exist."));
        if (report.isPresent()) {
            reportRepository.deleteById(id);
            log.debug("Deleted report with ID " + id + " from DB.");
            return "Report with ID " + id + " deleted successfully.";
        } else {
            return "Report with ID " + id + " is not present in DB.";
        }
    }
}

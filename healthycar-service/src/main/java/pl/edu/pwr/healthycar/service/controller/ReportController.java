package pl.edu.pwr.healthycar.service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.persistence.model.Report;
import pl.edu.pwr.healthycar.service.service.ReportService;

import java.util.List;

import static pl.edu.pwr.healthycar.service.utilities.Endpoints.*;

@RestController
@AllArgsConstructor
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(REPORTS)
    public List<Report> getReports() {
        log.debug(buildRequestLog(REPORTS));
        List<Report> reports = reportService.getReports();
        log.debug(buildResponseLog(REPORTS) + reports);
        return reports;
    }

    @GetMapping(REPORTS_ID)
    public Report getReport(@PathVariable String id) {
        log.debug(buildRequestLog(REPORTS_ID) + id);
        Report report = reportService.getReport(id);
        log.debug(buildResponseLog(REPORTS_ID) + report);
        return report;
    }

    @GetMapping(REPORTS_CAR_CARID)
    public List<Report> getCarReports(@PathVariable String carId) {
        log.debug(buildRequestLog(REPORTS_CAR_CARID) + carId);
        List<Report> carReports = reportService.getCarReports(carId);
        log.debug(buildResponseLog(REPORTS_CAR_CARID) + carReports);
        return carReports;
    }

    @PostMapping(REPORTS_SAVE)
    public Report upsertReport(@RequestBody Report report) {
        log.debug(buildRequestLog(REPORTS_SAVE));
        Report savedReport = reportService.upsertReport(report);
        log.debug(buildResponseLog(REPORTS_SAVE) + savedReport);
        return savedReport;
    }

    @DeleteMapping(REPORTS_DELETE_ID)
    public String deleteReport(@PathVariable String id) {
        log.debug(buildRequestLog(REPORTS_DELETE_ID) + id);
        String deleteResult = reportService.deleteReport(id);
        log.debug(buildResponseLog(REPORTS_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

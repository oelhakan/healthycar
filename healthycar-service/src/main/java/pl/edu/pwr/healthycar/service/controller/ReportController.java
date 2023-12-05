package pl.edu.pwr.healthycar.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Report;
import pl.edu.pwr.healthycar.api.service.ReportApi;
import pl.edu.pwr.healthycar.service.service.ReportService;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

@RestController
@Slf4j
public class ReportController implements ReportApi {

    @Autowired
    private ReportService reportService;

    @Override
    public List<Report> getReports() {
        log.debug(buildRequestLog(REPORTS));
        List<Report> reports = reportService.getReports();
        log.debug(buildResponseLog(REPORTS) + reports);
        return reports;
    }

    @Override
    public Report getReport(String id) {
        log.debug(buildRequestLog(REPORTS_ID) + id);
        Report report = reportService.getReport(id);
        log.debug(buildResponseLog(REPORTS_ID) + report);
        return report;
    }

    @Override
    public List<Report> getCarReports(String carId) {
        log.debug(buildRequestLog(REPORTS_CAR_CARID) + carId);
        List<Report> carReports = reportService.getCarReports(carId);
        log.debug(buildResponseLog(REPORTS_CAR_CARID) + carReports);
        return carReports;
    }

    @Override
    public Report upsertReport(Report report) {
        log.debug(buildRequestLog(REPORTS_SAVE));
        Report savedReport = reportService.upsertReport(report);
        log.debug(buildResponseLog(REPORTS_SAVE) + savedReport);
        return savedReport;
    }

    @Override
    public String deleteReport(String id) {
        log.debug(buildRequestLog(REPORTS_DELETE_ID) + id);
        String deleteResult = reportService.deleteReport(id);
        log.debug(buildResponseLog(REPORTS_DELETE_ID) + deleteResult);
        return deleteResult;
    }
}

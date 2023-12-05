package pl.edu.pwr.healthycar.api.service;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.healthycar.api.model.Report;

import java.util.List;

import static pl.edu.pwr.healthycar.api.utilities.Endpoints.*;

public interface ReportApi {

    @GetMapping(REPORTS)
    List<Report> getReports();

    @GetMapping(REPORTS_ID)
    Report getReport(@PathVariable String id);

    @GetMapping(REPORTS_CAR_CARID)
    List<Report> getCarReports(@PathVariable String carId);

    @PostMapping(REPORTS_SAVE)
    Report upsertReport(@RequestBody Report report);

    @DeleteMapping(REPORTS_DELETE_ID)
    String deleteReport(@PathVariable String id);
}

package pl.edu.pwr.healthycar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.healthycar.model.Report;
import pl.edu.pwr.healthycar.service.ReportService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("reports")
    public List<Report> getReports(){
        return reportService.getAllReports();
    }
}

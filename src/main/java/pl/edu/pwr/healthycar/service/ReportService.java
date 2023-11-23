package pl.edu.pwr.healthycar.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pwr.healthycar.model.Report;
import pl.edu.pwr.healthycar.repository.ReportRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }
}

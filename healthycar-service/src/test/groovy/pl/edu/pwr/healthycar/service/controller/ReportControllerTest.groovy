package pl.edu.pwr.healthycar.service.controller

import pl.edu.pwr.healthycar.api.model.Report
import pl.edu.pwr.healthycar.service.service.ReportService
import spock.lang.Specification

class ReportControllerTest extends Specification {

    def reportService = Mock(ReportService)
    def reportController = new ReportController(reportService: reportService)

    def 'should get all reports from reportService'(){
        given:
        def report1 = Mock(Report)
        def report2 = Mock(Report)
        def reports = [report1, report2]

        when:
        def result = reportController.getReports()

        then:
        1 * reportService.getReports() >> reports
        0 * _

        and:
        result == reports
    }

    def 'should get report with id from reportService'(){
        given:
        def reportId = "65689b42444cbf0c24cabcf5"
        def report = Mock(Report)

        when:
        def result = reportController.getReport(reportId)

        then:
        1 * reportService.getReport(reportId) >> report
        0 * _

        and:
        result == report
    }

    def 'should get cars reports from reportService'(){
        given:
        def carId = "6558c44e215f7deeb2ec2ed7"
        def report1 = Mock(Report)
        def report2 = Mock(Report)
        def reports = [report1, report2]

        when:
        def result = reportController.getCarReports(carId)

        then:
        1 * reportService.getCarReports(carId) >> reports
        0 * _

        and:
        result == reports
    }

    def 'should send the report to be updated/inserted to reportService'(){
        given:
        def report = Mock(Report)

        when:
        def result = reportController.upsertReport(report)

        then:
        1 * reportService.upsertReport(report) >> report
        0 * _

        and:
        result == report
    }

    def 'should send ID of the report to be deleted to reportService'(){
        given:
        def deleteResult = "Report with ID 6558c44e215f7deeb2ec2ed7 deleted successfully."

        when:
        def result = reportController.deleteReport("6558c44e215f7deeb2ec2ed7")

        then:
        1 * reportService.deleteReport("6558c44e215f7deeb2ec2ed7") >> deleteResult
        0 * _

        and:
        result == deleteResult
    }
}

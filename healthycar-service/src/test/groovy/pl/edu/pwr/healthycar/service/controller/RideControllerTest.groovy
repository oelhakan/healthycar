package pl.edu.pwr.healthycar.service.controller

import pl.edu.pwr.healthycar.api.model.Ride
import pl.edu.pwr.healthycar.service.service.RideService
import spock.lang.Specification

class RideControllerTest extends Specification {

    def rideService = Mock(RideService)
    def rideController = new RideController(rideService: rideService)

    def rideId = '6558c44e3da3080fcf9da95a'
    def userId = '6558c44eaecff28d670c45df'

    def 'should get all rides from rideService'() {
        given:
        def ride1 = Mock(Ride)
        def ride2 = Mock(Ride)
        def rides = [ride1, ride2]

        when:
        def result = rideController.getRides()

        then:
        1 * rideService.getRides() >> rides
        0 * _

        and:
        result == rides
    }

    def 'should get ride with id from rideService'() {
        given:
        def ride = Mock(Ride)

        when:
        def result = rideController.getRide(rideId)

        then:
        1 * rideService.getRide(rideId) >> ride
        0 * _

        and:
        result == ride
    }

    def 'should get user rides from ridesService'() {
        given:
        def ride1 = Mock(Ride)
        def ride2 = Mock(Ride)
        def rides = [ride1, ride2]

        when:
        def result = rideController.getUserRides(userId)

        then:
        1 * rideService.getUserRides(userId) >> rides
        0 * _

        and:
        result == rides
    }

    def 'should get latest ride of car from rideService'() {
        given:
        def ride = Mock(Ride)

        when:
        def result = rideController.getLatestCarRide(userId)

        then:
        1 * rideService.getLatestCarRide(userId) >> ride
        0 * _

        and:
        result == ride
    }

    def 'should send the ride to be updated/inserted to rideService'() {
        given:
        def ride = Mock(Ride)

        when:
        def result = rideController.upsertRide(ride)

        then:
        1 * rideService.upsertRide(ride) >> ride
        0 * _

        and:
        result == ride
    }

    def 'should send ID of the ride to be deleted to rideService'() {
        given:
        def deleteResult = 'Ride with ID 6558c44e3da3080fcf9da95a deleted successfully.'

        when:
        def result = rideController.deleteRide(rideId)

        then:
        1 * rideService.deleteRide(rideId) >> deleteResult
        0 * _

        and:
        result == deleteResult
    }
}

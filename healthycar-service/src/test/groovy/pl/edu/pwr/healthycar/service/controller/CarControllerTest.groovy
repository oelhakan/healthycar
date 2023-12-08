package pl.edu.pwr.healthycar.service.controller

import pl.edu.pwr.healthycar.api.model.Car
import pl.edu.pwr.healthycar.service.service.CarService
import spock.lang.Specification

class CarControllerTest extends Specification {

    def carService = Mock(CarService)
    def carController = new CarController(carService: carService)

    def carId = "65689b42444cbf0c24cabcf5"
    def ownerId = "6558c44eaecff28d670c45df"

    def 'should get all cars from carService'() {
        given:
        def car1 = Mock(Car)
        def car2 = Mock(Car)
        def cars = [car1, car2]

        when:
        def result = carController.getCars()

        then:
        1 * carService.getCars() >> cars
        0 * _

        and:
        result == cars
    }

    def 'should get car with id from carService'() {
        given:
        def car = Mock(Car)

        when:
        def result = carController.getCar(carId)

        then:
        1 * carService.getCar(carId) >> car
        0 * _

        and:
        result == car
    }

    def 'should get users cars from carService'() {
        given:
        def car1 = Mock(Car)
        def car2 = Mock(Car)
        def cars = [car1, car2]

        when:
        def result = carController.getUserCars(ownerId)

        then:
        1 * carService.getUserCars(ownerId) >> cars
        0 * _

        and:
        result == cars
    }

    def 'should send the car to be updated/inserted to carService'() {
        given:
        def car = Mock(Car)

        when:
        def result = carController.upsertCar(car)

        then:
        1 * carService.upsertCar(car) >> car
        0 * _

        and:
        result == car
    }

    def 'should send ID of the car to be deleted to carService'() {
        given:
        def deleteResult = "Car with ID 65689b42444cbf0c24cabcf5 deleted successfully."

        when:
        def result = carController.deleteCar(carId)

        then:
        1 * carService.deleteCar(carId) >> deleteResult
        0 * _

        and:
        result == deleteResult
    }
}

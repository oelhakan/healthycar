package pl.edu.pwr.healthycar.controller

import pl.edu.pwr.healthycar.repository.CarRepository
import pl.edu.pwr.healthycar.repository.UserRepository
import spock.lang.Specification

class CarControllerTest extends Specification {

    def carRepository = Mock(CarRepository)
    def userRepository = Mock(UserRepository)
    def carController = new CarController(carRepository, userRepository)

    def 'test'(){
        when:
        def result = carController.getCars()

        then:
        1 * carRepository.findAll() >> []
        0 * _
        print(result)
    }
}

package pl.edu.pwr.healthycar.service.service

import org.bson.types.ObjectId
import org.springframework.web.server.ResponseStatusException
import pl.edu.pwr.healthycar.api.model.Car
import pl.edu.pwr.healthycar.api.model.User
import pl.edu.pwr.healthycar.persistence.repository.CarRepository
import pl.edu.pwr.healthycar.persistence.repository.UserRepository
import spock.lang.Specification

class CarServiceTest extends Specification {

    def carRepository = Mock(CarRepository)
    def userRepository = Mock(UserRepository)
    def carService = new CarService(
            carRepository: carRepository,
            userRepository: userRepository)

    def carId = '65689b42444cbf0c24cabcf5'
    def ownerId = '6558c44eaecff28d670c45df'

    def 'should find and return all cars from db'() {
        given:
        def car1 = Mock(Car)
        def car2 = Mock(Car)
        def cars = [car1, car2]

        when:
        def result = carService.getCars()

        then:
        1 * carRepository.findAll() >> cars
        0 * _

        and:
        result == cars
    }

    def 'should find and return car with given id from db - success'() {
        given:
        def car = Mock(Car)
        def carOptional = Optional.of(car)

        when:
        def result = carService.getCar(carId)

        then:
        1 * carRepository.findById(new ObjectId(carId)) >> carOptional
        0 * _

        and:
        result == car
    }

    def 'should find and return car with given id from db - fail'() {
        given:
        def carOptional = Optional.empty()

        when:
        def result = carService.getCar(carId)

        then:
        1 * carRepository.findById(new ObjectId(carId)) >> carOptional
        0 * _

        and:
        result == null
    }

    def 'should find and return cars of given user from db'() {
        given:
        def car1 = Mock(Car)
        def car2 = Mock(Car)
        def cars = [car1, car2]

        when:
        def result = carService.getUserCars(ownerId)

        then:
        1 * carRepository.findAllByOwnerId(ownerId) >> cars
        0 * _

        and:
        result == cars
    }

    def 'should update car and return saved car - admin'() {
        given:
        def car = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2017).build()
        def carOptional = Optional.of(car)
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(true)
                .isFO(false)
                .carCount(5).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(carId) >> carOptional
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should update car and return saved car - FO'() {
        given:
        def car = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2017).build()
        def carOptional = Optional.of(car)
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(false)
                .isFO(true)
                .carCount(5).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(carId) >> carOptional
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should update car and return saved car - admin FO'() {
        given:
        def car = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2017).build()
        def carOptional = Optional.of(car)
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(true)
                .isFO(true)
                .carCount(5).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(carId) >> carOptional
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should update car and return saved car - user < 2 cars'() {
        given:
        def car = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2017).build()
        def carOptional = Optional.of(car)
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(false)
                .isFO(false)
                .carCount(1).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(carId) >> carOptional
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should update car and return saved car - user >= 2 cars'() {
        given:
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(false)
                .isFO(false)
                .carCount(2).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        def exception = thrown(ResponseStatusException)
        exception.message == '403 FORBIDDEN \"Car Count Limit Exceeded!\"'
        0 * _

        and:
        result == null
    }

    def 'should insert new car and return saved car - admin FO'() {
        given:
        def carOptional = Optional.empty()
        def newCar = Car.builder()
                .id(carId)
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(true)
                .isFO(true)
                .carCount(5).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(carId) >> carOptional
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should insert new car with autogenerated id and return saved car - admin FO'() {
        given:
        def newCar = Car.builder()
                .ownerId(ownerId)
                .year(2019).build()
        def owner = User.builder()
                .isAdmin(true)
                .isFO(true)
                .carCount(5).build()
        def ownerOptional = Optional.of(owner)

        when:
        def result = carService.upsertCar(newCar)

        then:
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * carRepository.findById(null) >> { throw new IllegalArgumentException() }
        1 * carRepository.save(newCar) >> newCar
        1 * userRepository.save(User.builder()
                .isAdmin(owner.getIsAdmin())
                .isFO(owner.getIsFO())
                .carCount(owner.getCarCount() + 1).build())
        0 * _

        and:
        result == newCar
    }

    def 'should delete car with given id from db - success'() {
        given:
        def owner = User.builder()
                .id(ownerId)
                .carCount(2).build()
        def ownerOptional = Optional.of(owner)
        def car = Car.builder()
                .id(carId)
                .ownerId(ownerId).build()
        def carOptional = Optional.of(car)
        def deleteResult = 'Car with ID 65689b42444cbf0c24cabcf5 deleted successfully.'

        when:
        def result = carService.deleteCar(carId)

        then:
        1 * carRepository.findById(new ObjectId(carId)) >> carOptional
        1 * userRepository.findById(new ObjectId(ownerId)) >> ownerOptional
        1 * userRepository.save(User.builder()
                .id(ownerId)
                .carCount(owner.getCarCount() - 1).build())
        1 * carRepository.deleteById(carId)
        0 * _

        and:
        result == deleteResult
    }

    def 'should delete car with given id from db - fail'() {
        given:
        def carOptional = Optional.empty()
        def deleteResult = 'Car with ID 65689b42444cbf0c24cabcf5 is not present in DB.'

        when:
        def result = carService.deleteCar(carId)

        then:
        1 * carRepository.findById(new ObjectId(carId)) >> carOptional
        0 * _

        and:
        result == deleteResult
    }
}

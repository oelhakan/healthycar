package pl.edu.pwr.healthycar.service.controller

import pl.edu.pwr.healthycar.api.model.LoginInfo
import pl.edu.pwr.healthycar.api.model.ResetInfo
import pl.edu.pwr.healthycar.api.model.User
import pl.edu.pwr.healthycar.service.service.UserService
import spock.lang.Specification

class UserControllerTest extends Specification {

    def userService = Mock(UserService)
    def userController = new UserController(userService: userService)

    def userId = '6558c44eaecff28d670c45df'

    def 'should get all users from userService'() {
        given:
        def user1 = Mock(User)
        def user2 = Mock(User)
        def users = [user1, user2]

        when:
        def result = userController.getUsers()

        then:
        1 * userService.getUsers() >> users
        0 * _

        and:
        result == users
    }

    def 'should get user with id from userService'() {
        given:
        def user = Mock(User)

        when:
        def result = userController.getUser(userId)

        then:
        1 * userService.getUser(userId) >> user
        0 * _

        and:
        result == user
    }

    def 'should send the user to be updated/inserted to userService'() {
        given:
        def user = Mock(User)

        when:
        def result = userController.upsertUser(user)

        then:
        1 * userService.upsertUser(user) >> user
        0 * _

        and:
        result == user
    }

    def 'should send ID of the user to be deleted to userService'() {
        given:
        def deleteResult = 'User with ID 6558c44eaecff28d670c45df deleted successfully.'

        when:
        def result = userController.deleteUser(userId)

        then:
        1 * userService.deleteUser(userId) >> deleteResult
        0 * _

        and:
        result == deleteResult
    }

    def 'should send login info of user to userService to log in'() {
        given:
        def loginInfo = Mock(LoginInfo)
        def user = Mock(User)

        when:
        def result = userController.login(loginInfo)

        then:
        1 * userService.login(loginInfo) >> user
        0 * _

        and:
        result == user
    }

    def 'should send reset info of user to userService to reset password'() {
        given:
        def resetInfo = new ResetInfo('atahanergurhan@bunga.com')
        def resetResult = 'Password reset successful for user atahanergurhan@bunga.com. Your new password has been sent to your email.'

        when:
        def result = userController.resetPassword(resetInfo)

        then:
        1 * userService.resetPassword(resetInfo.getEmail()) >> resetResult
        0 * _

        and:
        result == resetResult
    }
}

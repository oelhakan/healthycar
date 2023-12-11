package pl.edu.pwr.healthycar.service.controller

import pl.edu.pwr.healthycar.api.model.LoginInfo
import pl.edu.pwr.healthycar.api.model.PasswordChange
import pl.edu.pwr.healthycar.api.model.PasswordReset
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
        1 * userService.getAll() >> users
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
        1 * userService.getOne(userId) >> user
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
        1 * userService.upsert(user) >> user
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
        1 * userService.delete(userId) >> deleteResult
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

    def 'should send password reset info of user to userService to reset password'() {
        given:
        def passwordReset = new PasswordReset('atahanergurhan@bunga.com')
        def resetResult = 'Password reset successful for user atahanergurhan@bunga.com. Your new password has been sent to your email.'

        when:
        def result = userController.resetPassword(passwordReset)

        then:
        1 * userService.resetPassword(passwordReset) >> resetResult
        0 * _

        and:
        result == resetResult
    }

    def 'should send password change info of user to userService to reset password'() {
        given:
        def passwordChange = new PasswordChange('65773072398edb03d4b81d85', 'currentPassword', 'newPassword')
        def changeResult = 'Password change successful for user atahanergurhan@bunga.com.'

        when:
        def result = userController.changePassword(passwordChange)

        then:
        1 * userService.changePassword(passwordChange) >> changeResult
        0 * _

        and:
        result == changeResult
    }
}

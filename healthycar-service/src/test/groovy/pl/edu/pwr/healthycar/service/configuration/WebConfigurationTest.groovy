package pl.edu.pwr.healthycar.service.configuration


import org.springframework.web.servlet.config.annotation.CorsRegistry
import spock.lang.Specification

class WebConfigurationTest extends Specification {

    def webConfiguration = new WebConfiguration()

    def 'should add mapping to registry'() {
        given:
        def corsRegistry = new CorsRegistry()

        when:
        webConfiguration.addCorsMappings(corsRegistry)

        then:
        0 * _

        and:
        corsRegistry.registrations.size() == 1
        corsRegistry.registrations.get(0).pathPattern == '/**'
    }
}

package no.nav.klage.kodeverkapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableSwagger2
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

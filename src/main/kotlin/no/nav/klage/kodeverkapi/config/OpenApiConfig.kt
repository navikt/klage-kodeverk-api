package no.nav.klage.kodeverkapi.config

import no.nav.klage.kodeverkapi.api.controller.KodeverkController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class OpenApiConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage(KodeverkController::class.java.packageName))
            .build()
            .pathMapping("/")
            .genericModelSubstitutes(ResponseEntity::class.java)
    }
}
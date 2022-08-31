package no.nav.klage.kodeverkapi.config

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("standard")
            .pathsToMatch("/**")
            .build()
    }
}
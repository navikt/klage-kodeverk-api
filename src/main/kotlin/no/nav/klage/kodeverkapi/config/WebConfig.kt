package no.nav.klage.kodeverkapi.config

import no.nav.klage.kodeverkapi.domain.StringToLanguageEnumConverter
import org.springframework.boot.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    @Bean
    fun tomcatLocaleLimit(): WebServerFactoryCustomizer<TomcatServletWebServerFactory> =
        WebServerFactoryCustomizer { factory ->
            factory.addConnectorCustomizers({ connector -> connector.maxLocaleCount = 50 })
        }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToLanguageEnumConverter())
    }
}

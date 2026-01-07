package no.nav.klage.kodeverkapi.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.core.convert.converter.Converter
import java.util.*

enum class LanguageEnum {
    @JsonProperty("nb")
    NB,
    @JsonProperty("en")
    EN,
    @JsonProperty("nn")
    NN,
}

class StringToLanguageEnumConverter : Converter<String, LanguageEnum?> {
    override fun convert(source: String): LanguageEnum {
        return LanguageEnum.valueOf(source.uppercase(Locale.getDefault()))
    }
}
package no.nav.klage.kodeverkapi.domain

import com.fasterxml.jackson.annotation.JsonProperty

enum class LanguageEnum {
    @JsonProperty("nb")
    NB,
    @JsonProperty("en")
    EN,
    @JsonProperty("nn")
    NN,
}
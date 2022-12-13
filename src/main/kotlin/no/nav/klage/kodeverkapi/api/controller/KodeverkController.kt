package no.nav.klage.kodeverkapi.api.controller

import no.nav.klage.kodeverkapi.api.view.*
import no.nav.klage.kodeverkapi.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kodeverk")
class KodeverkController {

    @GetMapping(produces = ["application/json"])
    fun getKodeverk(): KodeverkResponse {
        return getKodeverkResponse()
    }

    @GetMapping("/ytelser/v1", produces = ["application/json"])
    fun getYtelserv1(): List<YtelseKode> {
        return getYtelseMap()
    }

    @GetMapping("/ytelser/v2", produces = ["application/json"])
    fun getYtelserv2(): List<YtelseKode> {
        return getYtelseMap()
    }

    @GetMapping("/tema", produces = ["application/json"])
    fun getTema(): List<KodeverkDto> {
        return getTemaList()
    }

    @GetMapping("/hjemler", produces = ["application/json"])
    fun getHjemlerDtos(): List<KodeverkDto> {
        return getHjemlerAsKodeverkDtos()
    }

    @GetMapping("/utfall", produces = ["application/json"])
    fun getUtfall(): List<KodeverkSimpleDto> {
        return getUtfallList()
    }

    @GetMapping("/enheter", produces = ["application/json"])
    fun getEnheter(): List<KodeverkSimpleDto> {
        return getEnhetList()
    }

    @GetMapping("/vedtaksenheter", produces = ["application/json"])
    fun getVedtaksenheter(): List<KodeverkSimpleDto> {
        return getVedtaksenhetList()
    }

    @GetMapping("/klageenheter", produces = ["application/json"])
    fun getKlageenheter(): List<KlageenhetKode> {
        return getKlageenhetToYtelserList()
    }

    @Deprecated(message = "Use /klageenheter instead. This can be deleted when FE are in prod")
    @GetMapping("/klageenhetertoytelser", produces = ["application/json"])
    fun getKlageenheterToYtelser(): List<KlageenhetKode> {
        return getKlageenhetToYtelserList()
    }

    @GetMapping("/klageenheterforankeinnsending", produces = ["application/json"])
    fun getKlageenheterForAnkeinnsending(): List<KodeverkSimpleDto> {
        return getKlageenheterForAnkeinnsendingList()
    }

    @GetMapping("/styringsenheter", produces = ["application/json"])
    fun getStyringsenheter(): List<KodeverkSimpleDto> {
        return getStyringsenhetList()
    }

    @GetMapping("/sakstyper", produces = ["application/json"])
    fun getSakstyper(): List<KodeverkSimpleDto> {
        return getTypeList()
    }

    @GetMapping("/sakstypertoutfall", produces = ["application/json"])
    fun getSakstyperToUtfall(): List<TypeToUtfallKode> {
        return getTypeMap()
    }

    @GetMapping("/sources", produces = ["application/json"])
    fun getSources(): List<KodeverkSimpleDto> {
        return getSourceList()
    }

    @GetMapping("/brevmottakertyper", produces = ["application/json"])
    fun getBrevmottakertyper(): List<KodeverkSimpleDto> {
        return getBrevmottakertypeList()
    }

    @GetMapping("/lovkildetoregistreringshjemler", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemler(): List<LovKildeToRegistreringshjemler> {
        return getLovkildeToRegistreringshjemlerList()
    }

    @GetMapping("/registreringshjemlermap", produces = ["application/json"])
    fun getRegistreringshjemler(): Map<String, LovKildeAndHjemmelnavn> {
        return getRegistreringshjemlerMap()
    }

    @GetMapping("/hjemlermap", produces = ["application/json"])
    fun getHjemler(): Map<String, String> {
        return getHjemlerMap()
    }
}
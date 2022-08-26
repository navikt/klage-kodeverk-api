package no.nav.klage.kodeverkapi.api.controller

import no.nav.klage.kodeverkapi.api.view.*
import no.nav.klage.kodeverkapi.util.*
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Unprotected
@RestController
@RequestMapping("/kodeverk")
class KodeverkController {

    @GetMapping(produces = ["application/json"])
    fun getKodeverk(): KodeverkResponse {
        return getKodeverkResponse()
    }

    @GetMapping("/ytelser", produces = ["application/json"])
    fun getYtelser(): List<KodeverkSimpleDto> {
        return getYtelseList()
    }

    @GetMapping("/tema", produces = ["application/json"])
    fun getTema(): List<KodeverkDto> {
        return getTemaList()
    }

    @GetMapping("/hjemler", produces = ["application/json"])
    fun getHjemler(): List<KodeverkDto> {
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
    fun getKlageenheter(): List<KodeverkSimpleDto> {
        return getKlageenhetList()
    }

    @GetMapping("/klageenheterforankeinnsending", produces = ["application/json"])
    fun getKlageenheterForAnkeinnsending(): List<KodeverkSimpleDto> {
        //TODO add missing enheter
        return getKlageenhetList()
    }

    @GetMapping("/styringsenheter", produces = ["application/json"])
    fun getStyringsenheter(): List<KodeverkSimpleDto> {
        return getStyringsenhetList()
    }

    @GetMapping("/sakstyper", produces = ["application/json"])
    fun getSakstyper(): List<KodeverkSimpleDto> {
        return getTypeList()
    }

    @GetMapping("/sources", produces = ["application/json"])
    fun getSources(): List<KodeverkSimpleDto> {
        return getSourceList()
    }

    @GetMapping("/brevmottakertyper", produces = ["application/json"])
    fun getBrevmottakertyper(): List<KodeverkSimpleDto> {
        return getBrevmottakertypeList()
    }
}
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

    @Unprotected
    @GetMapping("/ytelser", produces = ["application/json"])
    fun getYtelser(): List<YtelseKode> {
        return getYtelseList()
    }

    @Unprotected
    @GetMapping("/tema", produces = ["application/json"])
    fun getTema(): List<KodeverkDto> {
        return getTemaList()
    }

    @Unprotected
    @GetMapping("/hjemler", produces = ["application/json"])
    fun getHjemler(): List<KodeverkDto> {
        return getHjemlerAsKodeverkDtos()
    }

    @Unprotected
    @GetMapping("/utfall", produces = ["application/json"])
    fun getUtfall(): List<KodeverkSimpleDto> {
        return getUtfallList()
    }

    @Unprotected
    @GetMapping("/enheter", produces = ["application/json"])
    fun getEnheter(): List<KodeverkSimpleDto> {
        return getEnhetList()
    }

    @Unprotected
    @GetMapping("/vedtaksenheter", produces = ["application/json"])
    fun getVedtaksenheter(): List<KodeverkSimpleDto> {
        return getVedtaksenhetList()
    }

    @Unprotected
    @GetMapping("/klageenheter", produces = ["application/json"])
    fun getKlageenheter(): List<KlageenhetKode> {
        return getKlageenhetList()
    }

    @Unprotected
    @GetMapping("/styringsenheter", produces = ["application/json"])
    fun getStyringsenheter(): List<KodeverkSimpleDto> {
        return getStyringsenhetList()
    }

    @Unprotected
    @GetMapping("/sakstyper", produces = ["application/json"])
    fun getSakstyper(): List<KodeverkSimpleDto> {
        return getTypeList()
    }

    @Unprotected
    @GetMapping("/sources", produces = ["application/json"])
    fun getSources(): List<KodeverkSimpleDto> {
        return getSourceList()
    }

    @Unprotected
    @GetMapping("/brevmottakertyper", produces = ["application/json"])
    fun getBrevmottakertyper(): List<KodeverkSimpleDto> {
        return getBrevmottakertypeList()
    }
}
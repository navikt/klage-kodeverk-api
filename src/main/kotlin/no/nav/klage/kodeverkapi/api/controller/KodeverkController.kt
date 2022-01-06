package no.nav.klage.kodeverkapi.api.controller

import no.nav.klage.kodeverkapi.api.view.KodeverkResponse
import no.nav.klage.kodeverkapi.util.getKodeverkResponse
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KodeverkController {

    @Unprotected
    @GetMapping("/kodeverk", produces = ["application/json"])
    fun getKodeverk(): KodeverkResponse {
        return getKodeverkResponse()
    }
}
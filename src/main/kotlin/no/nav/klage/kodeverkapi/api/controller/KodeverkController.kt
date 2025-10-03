package no.nav.klage.kodeverkapi.api.controller

import no.nav.klage.kodeverkapi.api.view.*
import no.nav.klage.kodeverkapi.domain.LanguageEnum
import no.nav.klage.kodeverkapi.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        return getYtelseMapV1().sortedBy { it.navn }
    }

    @GetMapping("/ytelser/v2", "/ytelser/latest", produces = ["application/json"])
    fun getYtelserv2(): List<YtelseKode> {
        return getYtelseMapV2().sortedBy { it.navn }
    }

    @GetMapping("/kabal/ytelser/latest", produces = ["application/json"])
    fun getKabalYtelser(): List<KabalytelseKode> {
        return getKabalytelserMap().sortedBy { it.navn }
    }

    @GetMapping("/ytelser", produces = ["application/json"])
    fun getYtelser(): List<YtelseKode> {
        return getYtelseMap().sortedBy { it.navn }
    }

    @GetMapping("/ytelser/simple", produces = ["application/json"])
    fun getYtelseList(): List<KodeverkSimpleDto> {
        return getSimpleYtelseList().sortedBy { it.navn }
    }

    @GetMapping("/ytelser/simple/{language}", produces = ["application/json"])
    fun getYtelseDisplaynameListForLanguage(@PathVariable language: LanguageEnum): List<KodeverkSimpleDto> {
        return getYtelseDisplaynameList(language).sortedBy { it.navn }
    }

    @GetMapping("/innsendingsytelser/{language}", produces = ["application/json"])
    fun getInnsendingsytelseDisplaynameListForLanguage(@PathVariable language: LanguageEnum): List<KodeverkSimpleDto> {
        return getInnsendingsytelseDisplaynameList(language).sortedBy { it.navn }
    }

    @GetMapping("/tema", produces = ["application/json"])
    fun getTema(): List<KodeverkDto> {
        return getTemaList()
    }

    @GetMapping("/tema/{temaId}/ytelser/latest", produces = ["application/json"])
    fun getYtelseListFromTema(@PathVariable temaId: String): List<KodeverkSimpleDto> {
        return getSimpleYtelseListForTema(temaId)
    }

    @GetMapping("/fagsystemer", produces = ["application/json"])
    fun getFagsystemer(): List<KodeverkFagsystemDto> {
        return getFagsystemList()
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
        return getTypeToUtfallMap()
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

    @GetMapping("/lovkildetoregistreringshjemler/v1", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemlerv1(): List<LovKildeToRegistreringshjemler> {
        return getLovkildeToRegistreringshjemlerListV1()
    }

    @GetMapping("/lovkildetoregistreringshjemler/latest", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemlerv2(): List<LovKildeToRegistreringshjemler> {
        return getLovkildeToRegistreringshjemlerListV2()
    }

    @GetMapping("/registreringshjemlermap", produces = ["application/json"])
    fun getRegistreringshjemler(): Map<String, LovKildeAndHjemmelnavn> {
        return getRegistreringshjemlerMap()
    }

    @GetMapping("/hjemlermap", produces = ["application/json"])
    fun getHjemler(): Map<String, String> {
        return getHjemlerMap()
    }

    @GetMapping("/fradeling-reasons", produces = ["application/json"])
    fun getFradelingReasons(): List<KodeverkDto> {
        return getFradelingReasonList()
    }

    @GetMapping("/satt-paa-vent-reasons", produces = ["application/json"])
    fun getSattPaaVentReasons(): List<KodeverkDto> {
        return getSattPaaVentReasonList()
    }

    @GetMapping("/sakstyper-to-satt-paa-vent-reasons", produces = ["application/json"])
    fun getSakstyperToSattPaaVentReasons(): List<TypeToSattPaaVentReasons> {
        return getTypeToSattPaaVentReasonMap()
    }
}
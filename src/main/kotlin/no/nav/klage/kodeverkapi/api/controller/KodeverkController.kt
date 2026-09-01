package no.nav.klage.kodeverkapi.api.controller

import no.nav.klage.kodeverkapi.api.view.KabalytelseKode
import no.nav.klage.kodeverkapi.api.view.KlageenhetKode
import no.nav.klage.kodeverkapi.api.view.KodeverkDto
import no.nav.klage.kodeverkapi.api.view.KodeverkFagsystemDto
import no.nav.klage.kodeverkapi.api.view.KodeverkResponse
import no.nav.klage.kodeverkapi.api.view.KodeverkSimpleDto
import no.nav.klage.kodeverkapi.api.view.KodeverkWithDeprecatedDto
import no.nav.klage.kodeverkapi.api.view.LovKildeAndHjemmelnavn
import no.nav.klage.kodeverkapi.api.view.LovKildeToHjemler
import no.nav.klage.kodeverkapi.api.view.LovKildeToRegistreringshjemler
import no.nav.klage.kodeverkapi.api.view.TypeToSattPaaVentReasons
import no.nav.klage.kodeverkapi.api.view.TypeToUtfallKode
import no.nav.klage.kodeverkapi.api.view.YtelseKode
import no.nav.klage.kodeverkapi.domain.LanguageEnum
import no.nav.klage.kodeverkapi.util.getBrevmottakertypeList
import no.nav.klage.kodeverkapi.util.getEnhetList
import no.nav.klage.kodeverkapi.util.getFagsystemList
import no.nav.klage.kodeverkapi.util.getFradelingReasonList
import no.nav.klage.kodeverkapi.util.getHjemlerAsKodeverkWithDeprecatedDto
import no.nav.klage.kodeverkapi.util.getHjemlerMap
import no.nav.klage.kodeverkapi.util.getInnsendingsytelseDisplaynameList
import no.nav.klage.kodeverkapi.util.getKabalytelserMap
import no.nav.klage.kodeverkapi.util.getKlageenhetToYtelserList
import no.nav.klage.kodeverkapi.util.getKlageenheterForAnkeinnsendingList
import no.nav.klage.kodeverkapi.util.getKodeverkResponse
import no.nav.klage.kodeverkapi.util.getLovkildeToHjemlerList
import no.nav.klage.kodeverkapi.util.getLovkildeToRegistreringshjemlerList
import no.nav.klage.kodeverkapi.util.getLovkildeToRegistreringshjemlerListV1
import no.nav.klage.kodeverkapi.util.getLovkildeToRegistreringshjemlerListV2
import no.nav.klage.kodeverkapi.util.getRegistreringshjemlerMap
import no.nav.klage.kodeverkapi.util.getSattPaaVentReasonList
import no.nav.klage.kodeverkapi.util.getSimpleYtelseList
import no.nav.klage.kodeverkapi.util.getSimpleYtelseListForTema
import no.nav.klage.kodeverkapi.util.getSourceList
import no.nav.klage.kodeverkapi.util.getStyringsenhetList
import no.nav.klage.kodeverkapi.util.getTemaList
import no.nav.klage.kodeverkapi.util.getTypeList
import no.nav.klage.kodeverkapi.util.getTypeToSattPaaVentReasonMap
import no.nav.klage.kodeverkapi.util.getTypeToUtfallMap
import no.nav.klage.kodeverkapi.util.getUtfallForSakstype
import no.nav.klage.kodeverkapi.util.getUtfallList
import no.nav.klage.kodeverkapi.util.getVedtaksenhetList
import no.nav.klage.kodeverkapi.util.getYtelseDisplaynameList
import no.nav.klage.kodeverkapi.util.getYtelseMap
import no.nav.klage.kodeverkapi.util.getYtelseMapV1
import no.nav.klage.kodeverkapi.util.getYtelseMapV2
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kodeverk")
class KodeverkController {
    // TODO: Sjekk om dette kan fjernes. Sikkert flere også.
    @GetMapping(produces = ["application/json"])
    fun getKodeverk(): KodeverkResponse = getKodeverkResponse()

    @GetMapping("/ytelser/v1", produces = ["application/json"])
    fun getYtelserv1(): List<YtelseKode> = getYtelseMapV1().sortedBy { it.navn }

    @GetMapping("/ytelser/v2", "/ytelser/latest", produces = ["application/json"])
    fun getYtelserv2(): List<YtelseKode> = getYtelseMapV2().sortedBy { it.navn }

    @GetMapping("/kabal/ytelser/latest", produces = ["application/json"])
    fun getKabalYtelser(): List<KabalytelseKode> = getKabalytelserMap().sortedBy { it.navn }

    @GetMapping("/ytelser", produces = ["application/json"])
    fun getYtelser(): List<YtelseKode> = getYtelseMap().sortedBy { it.navn }

    @GetMapping("/ytelser/simple", produces = ["application/json"])
    fun getYtelseList(): List<KodeverkSimpleDto> = getSimpleYtelseList().sortedBy { it.navn }

    @GetMapping("/ytelser/simple/{language}", produces = ["application/json"])
    fun getYtelseDisplaynameListForLanguage(
        @PathVariable language: LanguageEnum,
    ): List<KodeverkSimpleDto> = getYtelseDisplaynameList(language).sortedBy { it.navn }

    @GetMapping("/innsendingsytelser/{language}", produces = ["application/json"])
    fun getInnsendingsytelseDisplaynameListForLanguage(
        @PathVariable language: LanguageEnum,
    ): List<KodeverkSimpleDto> = getInnsendingsytelseDisplaynameList(language).sortedBy { it.navn }

    @GetMapping("/tema", produces = ["application/json"])
    fun getTema(): List<KodeverkDto> = getTemaList()

    @GetMapping("/tema/{temaId}/ytelser/latest", produces = ["application/json"])
    fun getYtelseListFromTema(
        @PathVariable temaId: String,
    ): List<KodeverkSimpleDto> = getSimpleYtelseListForTema(temaId)

    @GetMapping("/fagsystemer", produces = ["application/json"])
    fun getFagsystemer(): List<KodeverkFagsystemDto> = getFagsystemList()

    @GetMapping("/hjemler", produces = ["application/json"])
    fun getHjemlerDtos(): List<KodeverkWithDeprecatedDto> = getHjemlerAsKodeverkWithDeprecatedDto()

    @GetMapping("/utfall", produces = ["application/json"])
    fun getUtfall(): List<KodeverkSimpleDto> = getUtfallList()

    @GetMapping("/enheter", produces = ["application/json"])
    fun getEnheter(): List<KodeverkSimpleDto> = getEnhetList()

    @GetMapping("/vedtaksenheter", produces = ["application/json"])
    fun getVedtaksenheter(): List<KodeverkSimpleDto> = getVedtaksenhetList()

    @GetMapping("/klageenheter", produces = ["application/json"])
    fun getKlageenheter(): List<KlageenhetKode> = getKlageenhetToYtelserList()

    @GetMapping("/klageenheterforankeinnsending", produces = ["application/json"])
    fun getKlageenheterForAnkeinnsending(): List<KodeverkSimpleDto> = getKlageenheterForAnkeinnsendingList()

    @GetMapping("/styringsenheter", produces = ["application/json"])
    fun getStyringsenheter(): List<KodeverkSimpleDto> = getStyringsenhetList()

    @GetMapping("/sakstyper", produces = ["application/json"])
    fun getSakstyper(): List<KodeverkSimpleDto> = getTypeList()

    @GetMapping("/sakstypertoutfall", produces = ["application/json"])
    fun getSakstyperToUtfall(): List<TypeToUtfallKode> = getTypeToUtfallMap()

    @GetMapping("/sakstypertoutfall/{sakstypeId}", produces = ["application/json"])
    fun getSakstyperToUtfall(
        @PathVariable sakstypeId: String,
    ): List<KodeverkSimpleDto> = getUtfallForSakstype(sakstypeId)

    @GetMapping("/sources", produces = ["application/json"])
    fun getSources(): List<KodeverkSimpleDto> = getSourceList()

    @GetMapping("/brevmottakertyper", produces = ["application/json"])
    fun getBrevmottakertyper(): List<KodeverkSimpleDto> = getBrevmottakertypeList()

    @GetMapping("/lovkildetohjemler", produces = ["application/json"])
    fun getLovKildeToHjemler(): List<LovKildeToHjemler> = getLovkildeToHjemlerList()

    @GetMapping("/lovkildetoregistreringshjemler", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemler(): List<LovKildeToRegistreringshjemler> = getLovkildeToRegistreringshjemlerList()

    @GetMapping("/lovkildetoregistreringshjemler/v1", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemlerv1(): List<LovKildeToRegistreringshjemler> = getLovkildeToRegistreringshjemlerListV1()

    @GetMapping("/lovkildetoregistreringshjemler/latest", produces = ["application/json"])
    fun getLovKildeToRegistreringshjemlerv2(): List<LovKildeToRegistreringshjemler> = getLovkildeToRegistreringshjemlerListV2()

    @GetMapping("/registreringshjemlermap", produces = ["application/json"])
    fun getRegistreringshjemler(): Map<String, LovKildeAndHjemmelnavn> = getRegistreringshjemlerMap()

    @GetMapping("/hjemlermap", produces = ["application/json"])
    fun getHjemler(): Map<String, String> = getHjemlerMap()

    @GetMapping("/fradeling-reasons", produces = ["application/json"])
    fun getFradelingReasons(): List<KodeverkDto> = getFradelingReasonList()

    @GetMapping("/satt-paa-vent-reasons", produces = ["application/json"])
    fun getSattPaaVentReasons(): List<KodeverkDto> = getSattPaaVentReasonList()

    @GetMapping("/sakstyper-to-satt-paa-vent-reasons", produces = ["application/json"])
    fun getSakstyperToSattPaaVentReasons(): List<TypeToSattPaaVentReasons> = getTypeToSattPaaVentReasonMap()
}

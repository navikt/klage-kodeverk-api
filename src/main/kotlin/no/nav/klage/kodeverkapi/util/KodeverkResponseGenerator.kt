package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.Hjemmel
import no.nav.klage.kodeverk.hjemmel.ytelseTilHjemler
import no.nav.klage.kodeverk.hjemmel.ytelseTilRegistreringshjemler
import no.nav.klage.kodeverkapi.api.view.*

fun getKodeverkResponse(): KodeverkResponse {
    return KodeverkResponse(
        ytelser = getYtelseList(),
        tema = getTemaList(),
        hjemler = getHjemlerAsKodeverkDtos(),
        utfall = getUtfallList(),
        enheter = getEnhetList(),
        vedtaksenheter = getVedtaksenhetList(),
        klageenheter = getKlageenhetList(),
        styringsenheter = getStyringsenhetList(),
        sakstyper = getTypeList(),
        sources = getSourceList(),
        brevmottakertyper = getBrevmottakertypeList(),
    )
}

fun getTemaList(): List<KodeverkDto> {
    return Tema.values().asList().toKodeverkDto()
}

fun getUtfallList(): List<KodeverkSimpleDto> {
    return Utfall.values().asList().toKodeverkSimpleDto()
}

fun getEnhetList(): List<KodeverkSimpleDto> {
    return Enhet.values().asList().toEnhetKodeverkSimpleDto()
}

fun getStyringsenhetList(): List<KodeverkSimpleDto> {
    return styringsenheter.toList().toEnhetKodeverkSimpleDto()
}

fun getTypeList(): List<KodeverkSimpleDto> {
    return Type.values().asList().toKodeverkSimpleDto()
}

fun getSourceList(): List<KodeverkSimpleDto> {
    return Source.values().asList().toKodeverkSimpleDto()
}

fun getBrevmottakertypeList(): List<KodeverkSimpleDto> {
    return Brevmottakertype.values().asList().toKodeverkSimpleDto()
}


fun getVedtaksenhetList(): List<KodeverkSimpleDto> {
    return Enhet.values().filter { it !in klageenheter && it !in styringsenheter }.toEnhetKodeverkSimpleDto()
}

fun getKlageenhetList(): List<KlageenhetKode> =
    klageenhetTilYtelser.map { klageenhetTilYtelse ->
        KlageenhetKode(
            id = klageenhetTilYtelse.key.navn,
            navn = klageenhetTilYtelse.key.beskrivelse,
            ytelser = klageenhetTilYtelse.value.toKodeverkSimpleDto()
        )
    }

fun getHjemlerAsKodeverkDtos(): List<KodeverkDto> {
    return Hjemmel.values().map {
        it.toKodeverkDto()
    }
}

private fun Hjemmel.toKodeverkDto() =
    KodeverkDto(
        id = id,
        navn = lovKilde.beskrivelse + " - " + spesifikasjon,
        beskrivelse = lovKilde.navn + " - " + spesifikasjon,
    )

private val ytelseToLovKildeToRegistreringshjemmel: Map<Ytelse, List<LovKildeToRegistreringshjemler>> =
    ytelseTilRegistreringshjemler.mapValues { (_, hjemler) ->
        hjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) }
        ).map { hjemmel ->
            LovKildeToRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value
            )
        }
    }

fun getYtelseList(): List<YtelseKode> =
    Ytelse.values().map { ytelse ->
        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmel[ytelse] ?: emptyList(),
            enheter = ytelseTilVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseTilKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseTilHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }

private fun Kode.toKodeverkDto() = KodeverkDto(id = id, navn = navn, beskrivelse = beskrivelse)

private fun Kode.toKodeverkSimpleDto() = KodeverkSimpleDto(id = id, navn = navn)

private fun Kode.toEnhetKodeverkSimpleDto() = KodeverkSimpleDto(id = navn, navn = beskrivelse)

private fun Collection<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }

private fun Collection<Kode>.toKodeverkSimpleDto() = map { it.toKodeverkSimpleDto() }

private fun Collection<Kode>.toEnhetKodeverkSimpleDto() = map { it.toEnhetKodeverkSimpleDto() }
package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.Hjemmel
import no.nav.klage.kodeverk.hjemmel.ytelseTilHjemler
import no.nav.klage.kodeverk.hjemmel.ytelseTilRegistreringshjemler
import no.nav.klage.kodeverkapi.api.view.*

fun getKodeverkResponse(): KodeverkResponse {
    return KodeverkResponse(
        ytelser = getYtelser(),
        tema = Tema.values().asList().toKodeverkDto(),
        hjemler = getHjemlerAsKodeverkDtos(),
        utfall = Utfall.values().asList().toKodeverkSimpleDto(),
        enheter = Enhet.values().asList().toEnhetKodeverkSimpleDto(),
        vedtaksenheter = getVedtaksenheter(),
        klageenheter = getKlageenheter(),
        styringsenheter = styringsenheter.toList().toEnhetKodeverkSimpleDto(),
        sakstyper = Type.values().asList().toKodeverkSimpleDto(),
        sources = Source.values().asList().toKodeverkSimpleDto(),
        brevmottakertyper = Brevmottakertype.values().asList().toKodeverkSimpleDto()
    )
}

private fun getVedtaksenheter(): List<KodeverkSimpleDto> {
    return Enhet.values().filter { it !in klageenheter && it !in styringsenheter }.toEnhetKodeverkSimpleDto()
}

private fun getKlageenheter(): List<KlageenhetKode> =
    klageenhetTilYtelser.map { klageenhetTilYtelse ->
        KlageenhetKode(
            id = klageenhetTilYtelse.key.navn,
            navn = klageenhetTilYtelse.key.beskrivelse,
            ytelser = klageenhetTilYtelse.value.toKodeverkSimpleDto()
        )
    }

private fun getHjemlerAsKodeverkDtos(): List<KodeverkDto> {
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

private fun getYtelser(): List<YtelseKode> =
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
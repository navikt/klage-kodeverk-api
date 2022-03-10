package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.Hjemmel
import no.nav.klage.kodeverk.hjemmel.ytelseTilRegistreringshjemler
import no.nav.klage.kodeverkapi.api.view.*

fun getKodeverkResponse(): KodeverkResponse {
    return KodeverkResponse(
        ytelser = getYtelser(),
        tema = Tema.values().asList().toKodeverkDto(),
        hjemler = getHjemlerAsKodeverkDtos(),
        utfall = Utfall.values().asList().toKodeverkSimpleDto(),
        enheter = Enhet.values().asList().toEnhetKodeverkDto(),
        vedtaksenheter = getVedtaksenheter(),
        klageenheter = getKlageenheter(),
        styringsenheter = styringsenheter.toList().toKodeverkDto(),
        sakstyper = Type.values().asList().toKodeverkSimpleDto(),
        sources = Source.values().asList().toKodeverkSimpleDto(),
    )
}

private fun getVedtaksenheter(): List<KodeverkDto> {
    return Enhet.values().filter { it !in klageenheter && it !in styringsenheter }.toEnhetKodeverkDto()
}

private fun Collection<Enhet>.toEnhetKodeverkDto(): List<KodeverkDto> {
    return map { KodeverkDto(id = it.navn, navn = it.beskrivelse, beskrivelse = it.beskrivelse) }
}

private fun getKlageenheter(): List<KlageenhetKode> =
    klageenhetTilYtelser.map { klageenhetTilYtelse ->
        KlageenhetKode(
            id = klageenhetTilYtelse.key.navn,
            navn = klageenhetTilYtelse.key.beskrivelse,
            beskrivelse = klageenhetTilYtelse.key.beskrivelse,
            ytelser = klageenhetTilYtelse.value.toKodeverkDto()
        )
    }

private fun getHjemlerAsKodeverkDtos(): List<KodeverkDto> {
    return Hjemmel.values().map {
        KodeverkDto(
            id = it.id,
            navn = it.lovKilde.beskrivelse + " - " + it.spesifikasjon,
            beskrivelse = it.lovKilde.navn + " - " + it.spesifikasjon,
        )
    }
}

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
            beskrivelse = ytelse.beskrivelse,
            lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmel[ytelse] ?: emptyList(),
            enheter = ytelseTilVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkDto() } ?: emptyList(),
            klageenheter = ytelseTilKlageenheter[ytelse]?.map { it.toEnhetKodeverkDto() } ?: emptyList(),
        )
    }

private fun Kode.toKodeverkDto() = KodeverkDto(id = id, navn = navn, beskrivelse = beskrivelse)

private fun Kode.toEnhetKodeverkDto() = KodeverkDto(id = navn, navn = beskrivelse, beskrivelse = beskrivelse)

private fun Kode.toKodeverkSimpleDto() = KodeverkSimpleDto(id = id, navn = navn)

private fun List<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }

private fun List<Kode>.toKodeverkSimpleDto() = map { it.toKodeverkSimpleDto() }

private fun Set<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }.toSet()
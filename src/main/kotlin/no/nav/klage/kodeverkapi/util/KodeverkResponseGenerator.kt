package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.Hjemmel
import no.nav.klage.kodeverk.hjemmel.Registreringshjemmel
import no.nav.klage.kodeverk.hjemmel.ytelseTilHjemler
import no.nav.klage.kodeverk.hjemmel.ytelseTilRegistreringshjemler
import no.nav.klage.kodeverkapi.api.view.*

fun getKodeverkResponse(): KodeverkResponse {
    return KodeverkResponse(
        ytelser = getYtelseMap(),
        tema = getTemaList(),
        hjemler = getHjemlerAsKodeverkDtos(),
        utfall = getUtfallList(),
        enheter = getEnhetList(),
        vedtaksenheter = getVedtaksenhetList(),
        klageenheter = getKlageenhetToYtelserList(),
        styringsenheter = getStyringsenhetList(),
        sakstyper = getTypeList(),
        sakstyperToUtfall = getTypeMap(),
        sources = getSourceList(),
        brevmottakertyper = getBrevmottakertypeList(),
    )
}

fun getTemaList() = Tema.values().asList().toKodeverkDto()

fun getUtfallList() = Utfall.values().asList().toKodeverkSimpleDto()

fun getEnhetList() = Enhet.values().asList().toEnhetKodeverkSimpleDto()

fun getStyringsenhetList() = styringsenheter.toList().toEnhetKodeverkSimpleDto()

fun getTypeList() = Type.values().asList().toKodeverkSimpleDto()

fun getSourceList() = Source.values().asList().toKodeverkSimpleDto()

fun getBrevmottakertypeList() = Brevmottakertype.values().asList().toKodeverkSimpleDto()

fun getVedtaksenhetList() =
    Enhet.values().filter { it !in klageenheter && it !in styringsenheter && it !in klageenheterForAnkeinnsending }
        .toEnhetKodeverkSimpleDto()

fun getKlageenhetList() = klageenheter.toEnhetKodeverkSimpleDto()

fun getKlageenheterForAnkeinnsendingList() = klageenheterForAnkeinnsending.toEnhetKodeverkSimpleDto()

fun getKlageenhetToYtelserList(): List<KlageenhetKode> =
    klageenhetTilYtelser.map { klageenhetTilYtelse ->
        KlageenhetKode(
            id = klageenhetTilYtelse.key.navn,
            navn = klageenhetTilYtelse.key.beskrivelse,
            ytelser = klageenhetTilYtelse.value.toKodeverkSimpleDto()
        )
    }

fun getHjemlerAsKodeverkDtos() = Hjemmel.values().map { it.toKodeverkDto() }

private fun Hjemmel.toKodeverkDto() =
    KodeverkDto(
        id = id,
        navn = lovKilde.beskrivelse + " - " + spesifikasjon,
        beskrivelse = lovKilde.navn + " - " + spesifikasjon,
    )

private val ytelseToLovKildeToRegistreringshjemmel: Map<Ytelse, List<LovKildeAndRegistreringshjemler>> =
    ytelseTilRegistreringshjemler.mapValues { (_, hjemler) ->
        hjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) }
        ).map { hjemmel ->
            LovKildeAndRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value
            )
        }
    }

fun getLovkildeToRegistreringshjemlerList(): List<LovKildeToRegistreringshjemler> {
    val hjemler = Registreringshjemmel.values()
    val lovkildeGrouping = hjemler.groupBy {
        it.lovKilde
    }.map { (lovkilde, registreringshjemler) ->
        LovKildeToRegistreringshjemler(
            id = lovkilde.id,
            navn = lovkilde.navn,
            beskrivelse = lovkilde.beskrivelse,
            registreringshjemler = registreringshjemler.map{
                KodeverkSimpleDto(it.id, it.spesifikasjon)
            }
        )
    }
    return lovkildeGrouping
}

fun getRegistreringshjemlerMap(): Map<String, String> {
    return Registreringshjemmel.values().map {
        it.id to "${it.lovKilde.beskrivelse} - ${it.spesifikasjon}"
    }.toMap()
}

fun getHjemlerMap(): Map<String, String> {
    return Hjemmel.values().map {
        it.id to "${it.lovKilde.beskrivelse} - ${it.spesifikasjon}"
    }.toMap()
}

fun getTypeMap(): List<TypeToUtfallKode> =
    Type.values().map { type ->
        TypeToUtfallKode(
            id = type.id,
            navn = type.navn,
            utfall = typeTilUtfall[type]?.map { it.toKodeverkSimpleDto() } ?: emptyList()
        )
    }

fun getYtelseMap(): List<YtelseKode> =
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

fun getYtelseList(): List<KodeverkSimpleDto> {
    return Ytelse.values().asList().toKodeverkSimpleDto()
}

private fun Kode.toKodeverkDto() = KodeverkDto(id = id, navn = navn, beskrivelse = beskrivelse)

private fun Kode.toKodeverkSimpleDto() = KodeverkSimpleDto(id = id, navn = navn)

private fun Kode.toEnhetKodeverkSimpleDto() = KodeverkSimpleDto(id = navn, navn = beskrivelse)

private fun Collection<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }

private fun Collection<Kode>.toKodeverkSimpleDto() = map { it.toKodeverkSimpleDto() }

private fun Collection<Kode>.toEnhetKodeverkSimpleDto() = map { it.toEnhetKodeverkSimpleDto() }
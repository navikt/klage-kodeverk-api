package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.*
import no.nav.klage.kodeverkapi.api.view.*

fun getKodeverkResponse(): KodeverkResponse {
    return KodeverkResponse(
        ytelser = getYtelseMapV1(),
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

fun getFagsystemList(): List<KodeverkFagsystemDto> {
    return Fagsystem.values().map {
        KodeverkFagsystemDto(
            id = it.id,
            navn = it.navn,
            beskrivelse = it.beskrivelse,
            modernized = it.modernized,
        )
    }
}

fun getUtfallList() = Utfall.values().asList().toKodeverkSimpleDto()

fun getEnhetList() = Enhet.values().asList().toEnhetKodeverkSimpleDto()

fun getStyringsenhetList() = styringsenheter.toList().toEnhetKodeverkSimpleDto()

fun getTypeList() = Type.values().asList().toKodeverkSimpleDto()

fun getSimpleYtelseList() = Ytelse.values().asList().toKodeverkSimpleDto()

fun getSimpleYtelseListForTema(temaId: String): List<KodeverkSimpleDto> {
    return Ytelse.values().asList().filter { it.toTema().id == temaId}.toKodeverkSimpleDto()
}

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

private val ytelseToLovKildeToRegistreringshjemmelV1: Map<Ytelse, List<LovKildeAndRegistreringshjemler>> =
    ytelseTilRegistreringshjemlerV1.mapValues { (_, hjemler) ->
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

private val ytelseToLovKildeToRegistreringshjemmelV2: Map<Ytelse, List<LovKildeAndRegistreringshjemler>> =
    ytelseTilRegistreringshjemlerV2.mapValues { (_, hjemler) ->
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
    val hjemler = Registreringshjemmel.values().toSet()
    val lovkildeGrouping = lovKildeToRegistreringshjemler(hjemler)
    return lovkildeGrouping
}

fun getLovkildeToRegistreringshjemlerListV1(): List<LovKildeToRegistreringshjemler> {
    val hjemler = ytelseTilRegistreringshjemlerV1.map { it.value }.flatten().toSet()
    val lovkildeGrouping = lovKildeToRegistreringshjemler(hjemler)
    return lovkildeGrouping
}

fun getLovkildeToRegistreringshjemlerListV2(): List<LovKildeToRegistreringshjemler> {
    val hjemler = ytelseTilRegistreringshjemlerV2.map { it.value }.flatten().toSet()
    val lovkildeGrouping = lovKildeToRegistreringshjemler(hjemler)
    return lovkildeGrouping
}

private fun lovKildeToRegistreringshjemler(hjemler: Set<Registreringshjemmel>): List<LovKildeToRegistreringshjemler> {
    val lovkildeGrouping = hjemler.groupBy {
        it.lovKilde
    }.map { (lovkilde, registreringshjemler) ->
        LovKildeToRegistreringshjemler(
            id = lovkilde.id,
            navn = lovkilde.navn,
            beskrivelse = lovkilde.beskrivelse,
            registreringshjemler = registreringshjemler.map {
                KodeverkSimpleDto(it.id, it.spesifikasjon)
            }
        )
    }
    return lovkildeGrouping
}

fun getRegistreringshjemlerMap(): Map<String, LovKildeAndHjemmelnavn> {
    return Registreringshjemmel.values().map {
        it.id to LovKildeAndHjemmelnavn(
            lovkilde = it.lovKilde.toKodeverkDto(),
            hjemmelnavn = it.spesifikasjon,
        )
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

fun getYtelseMapV1(): List<YtelseKode> =
    Ytelse.values().map { ytelse ->
        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmelV1[ytelse] ?: emptyList(),
            enheter = ytelseTilVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseTilKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseTilHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }

fun getYtelseMapV2(): List<YtelseKode> =
    Ytelse.values().map { ytelse ->
        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmelV2[ytelse] ?: emptyList(),
            enheter = ytelseTilVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseTilKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseTilHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }

fun getYtelseMap(): List<YtelseKode> {
    return Ytelse.values().map { ytelse ->
        val allRegistreringshjemler = setOf(
            ytelseTilRegistreringshjemlerV1[ytelse],
            ytelseTilRegistreringshjemlerV2[ytelse]
        ).flatMap { it?.toSet() ?: emptySet() }.toSet().sortedBy { it.spesifikasjon }
        val lovKildeToRegistreringshjemler = allRegistreringshjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) },
        ).map { hjemmel ->
            LovKildeAndRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value
            )
        }

        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = lovKildeToRegistreringshjemler,
            enheter = ytelseTilVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseTilKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseTilHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }
}

private fun Kode.toKodeverkDto() = KodeverkDto(id = id, navn = navn, beskrivelse = beskrivelse)

private fun Kode.toKodeverkSimpleDto() = KodeverkSimpleDto(id = id, navn = navn)

private fun Kode.toEnhetKodeverkSimpleDto() = KodeverkSimpleDto(id = navn, navn = beskrivelse)

private fun Collection<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }

private fun Collection<Kode>.toKodeverkSimpleDto() = map { it.toKodeverkSimpleDto() }

private fun Collection<Kode>.toEnhetKodeverkSimpleDto() = map { it.toEnhetKodeverkSimpleDto() }

//Test commit
package no.nav.klage.kodeverkapi.util

import no.nav.klage.kodeverk.*
import no.nav.klage.kodeverk.hjemmel.*
import no.nav.klage.kodeverk.innsendingsytelse.Innsendingsytelse
import no.nav.klage.kodeverk.innsendingsytelse.innsendingsytelseToDisplayName
import no.nav.klage.kodeverk.ytelse.Ytelse
import no.nav.klage.kodeverk.ytelse.ytelseToDisplayName
import no.nav.klage.kodeverk.ytelse.ytelseToKlageenheter
import no.nav.klage.kodeverk.ytelse.ytelseToVedtaksenheter
import no.nav.klage.kodeverkapi.api.view.*
import no.nav.klage.kodeverkapi.domain.LanguageEnum

val kodeverkSimpleDtoComparator = Comparator<KodeverkSimpleDto> { o1, o2 ->
    val firstNavn = o1?.navn
    val secondNavn = o2?.navn
    hjemmelComparator.compare(firstNavn, secondNavn)
}

val kodeverkDtoComparator = Comparator<KodeverkDto> { o1, o2 ->
    val firstNavn = o1?.navn
    val secondNavn = o2?.navn
    hjemmelComparator.compare(firstNavn, secondNavn)
}

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

fun getTemaList() = Tema.entries.toKodeverkDto()

fun getFagsystemList(): List<KodeverkFagsystemDto> {
    return Fagsystem.entries.map {
        KodeverkFagsystemDto(
            id = it.id,
            navn = it.navn,
            beskrivelse = it.beskrivelse,
            modernized = it.modernized,
        )
    }
}

fun getUtfallList() = Utfall.entries.toKodeverkSimpleDto()

fun getEnhetList() = Enhet.entries.toEnhetKodeverkSimpleDto()

fun getStyringsenhetList() = styringsenheter.toList().toEnhetKodeverkSimpleDto()

fun getTypeList() = Type.entries.toKodeverkSimpleDto()

fun getSimpleYtelseList() = Ytelse.entries.toKodeverkSimpleDto()

fun getSimpleYtelseListForTema(temaId: String): List<KodeverkSimpleDto> {
    return Ytelse.entries.filter { it.toTema().id == temaId }.toKodeverkSimpleDto()
}

fun getSourceList() = Source.entries.toKodeverkSimpleDto()

fun getFradelingReasonList() = FradelingReason.entries.toKodeverkSimpleDto()

fun getSattPaaVentReasonList() = SattPaaVentReason.entries.toKodeverkSimpleDto()

fun getBrevmottakertypeList() = Brevmottakertype.entries.toKodeverkSimpleDto()

fun getVedtaksenhetList() =
    Enhet.entries.filter { it !in klageenheter && it !in styringsenheter && it !in klageenheterForAnkeinnsending }
        .toEnhetKodeverkSimpleDto()

fun getKlageenheterForAnkeinnsendingList() = klageenheterForAnkeinnsending.toEnhetKodeverkSimpleDto()

fun getKlageenhetToYtelserList(): List<KlageenhetKode> =
    klageenhetToYtelser.map { klageenhetTilYtelse ->
        KlageenhetKode(
            id = klageenhetTilYtelse.key.navn,
            navn = klageenhetTilYtelse.key.beskrivelse,
            ytelser = klageenhetTilYtelse.value.toKodeverkSimpleDto().sortedBy { it.navn }
        )
    }

fun getHjemlerAsKodeverkDtos() = Hjemmel.entries.map { it.toKodeverkDto() }.sortedWith(kodeverkDtoComparator)

private fun Hjemmel.toKodeverkDto() =
    KodeverkDto(
        id = id,
        navn = lovKilde.beskrivelse + " - " + spesifikasjon,
        beskrivelse = lovKilde.navn + " - " + spesifikasjon,
    )

private val ytelseToLovKildeToRegistreringshjemmelV1: Map<Ytelse, List<LovKildeAndRegistreringshjemler>> =
    ytelseToRegistreringshjemlerV1.mapValues { (_, hjemler) ->
        hjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) }
        ).map { hjemmel ->
            LovKildeAndRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value.sortedWith(kodeverkSimpleDtoComparator)
            )
        }
    }

private val ytelseToLovKildeToRegistreringshjemmelV2: Map<Ytelse, List<LovKildeAndRegistreringshjemler>> =
    ytelseToRegistreringshjemlerV2.mapValues { (_, hjemler) ->
        hjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) }
        ).map { hjemmel ->
            LovKildeAndRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value.sortedWith(kodeverkSimpleDtoComparator)
            )
        }
    }

fun getLovkildeToRegistreringshjemlerList(): List<LovKildeToRegistreringshjemler> {
    val hjemler = Registreringshjemmel.entries.toSet()
    val lovkildeGrouping = lovKildeToRegistreringshjemler(hjemler)
    return lovkildeGrouping
}

fun getLovkildeToRegistreringshjemlerListV1(): List<LovKildeToRegistreringshjemler> {
    val hjemler = ytelseToRegistreringshjemlerV1.map { it.value }.flatten().toSet()
    val lovkildeGrouping = lovKildeToRegistreringshjemler(hjemler)
    return lovkildeGrouping
}

fun getLovkildeToRegistreringshjemlerListV2(): List<LovKildeToRegistreringshjemler> {
    val hjemler = ytelseToRegistreringshjemlerV2.map { it.value }.flatten().toSet()
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
            }.sortedWith(kodeverkSimpleDtoComparator)
        )
    }
    return lovkildeGrouping
}

fun getRegistreringshjemlerMap(): Map<String, LovKildeAndHjemmelnavn> {
    return Registreringshjemmel.entries.map {
        it.id to LovKildeAndHjemmelnavn(
            lovkilde = it.lovKilde.toKodeverkDto(),
            hjemmelnavn = it.spesifikasjon,
        )
    }.toMap()
}

fun getHjemlerMap(): Map<String, String> {
    return Hjemmel.entries.map {
        it.id to "${it.lovKilde.beskrivelse} - ${it.spesifikasjon}"
    }.toMap()
}

fun getTypeMap(): List<TypeToUtfallKode> =
    Type.entries.map { type ->
        //Make sure "Retur" is at end of list
        val utfallList = typeToUtfall[type]?.minus(Utfall.RETUR)?.toMutableList()
        if (typeToUtfall[type]?.contains(Utfall.RETUR) == true) {
            utfallList!!.add(Utfall.RETUR)
        }

        TypeToUtfallKode(
            id = type.id,
            navn = type.navn,
            utfall = utfallList?.map { it.toKodeverkSimpleDto() } ?: emptyList()
        )
    }

fun getYtelseMapV1(): List<YtelseKode> =
    Ytelse.entries.map { ytelse ->
        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmelV1[ytelse] ?: emptyList(),
            enheter = ytelseToVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseToKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseToHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }

fun getYtelseMapV2(): List<YtelseKode> =
    Ytelse.entries.map { ytelse ->
        ytelseKodeV2(ytelse)
    }

fun getKabalytelserMap(): List<KabalytelseKode> =
    ytelseToHjemler.keys.map { ytelse ->
        getKabalYtelseKodeV2(ytelse)
    }

private fun getKabalYtelseKodeV2(ytelse: Ytelse) = KabalytelseKode(
    id = ytelse.id,
    navn = ytelse.navn,
    lovKildeToRegistreringshjemler = lovKildeToRegistreringshjemler(ytelseToRegistreringshjemlerV2[ytelse]!!.toSet())
)

private fun ytelseKodeV2(ytelse: Ytelse) = YtelseKode(
    id = ytelse.id,
    navn = ytelse.navn,
    lovKildeToRegistreringshjemler = ytelseToLovKildeToRegistreringshjemmelV2[ytelse] ?: emptyList(),
    enheter = ytelseToVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
    klageenheter = ytelseToKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
    innsendingshjemler = ytelseToHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
)

fun getYtelseMap(): List<YtelseKode> {
    return Ytelse.entries.map { ytelse ->
        val allRegistreringshjemler = setOf(
            ytelseToRegistreringshjemlerV1[ytelse],
            ytelseToRegistreringshjemlerV2[ytelse]
        ).flatMap { it?.toSet() ?: emptySet() }.toSet().sortedBy { it.spesifikasjon }
        val lovKildeToRegistreringshjemler = allRegistreringshjemler.groupBy(
            { hjemmel -> hjemmel.lovKilde },
            { hjemmel -> KodeverkSimpleDto(hjemmel.id, hjemmel.spesifikasjon) },
        ).map { hjemmel ->
            LovKildeAndRegistreringshjemler(
                hjemmel.key.toKodeverkDto(),
                hjemmel.value.sortedWith(kodeverkSimpleDtoComparator)
            )
        }

        YtelseKode(
            id = ytelse.id,
            navn = ytelse.navn,
            lovKildeToRegistreringshjemler = lovKildeToRegistreringshjemler,
            enheter = ytelseToVedtaksenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            klageenheter = ytelseToKlageenheter[ytelse]?.map { it.toEnhetKodeverkSimpleDto() } ?: emptyList(),
            innsendingshjemler = ytelseToHjemler[ytelse]?.map { it.toKodeverkDto() } ?: emptyList()
        )
    }
}

fun getYtelseDisplaynameList(language: LanguageEnum): List<KodeverkSimpleDto> {
    return Ytelse.entries.map { ytelse ->
        KodeverkSimpleDto(
            id = ytelse.id,
            navn = when (language) {
                LanguageEnum.NB -> ytelseToDisplayName[ytelse]!!.nb
                LanguageEnum.EN -> ytelseToDisplayName[ytelse]!!.en
                LanguageEnum.NN -> ytelseToDisplayName[ytelse]!!.nn
            }
        )
    }
}

fun getInnsendingsytelseDisplaynameList(language: LanguageEnum): List<KodeverkSimpleDto> {
    return Innsendingsytelse.entries.map { innsendingsytelse ->
        KodeverkSimpleDto(
            id = innsendingsytelse.id,
            navn = when (language) {
                LanguageEnum.NB -> innsendingsytelseToDisplayName[innsendingsytelse]!!.nb
                LanguageEnum.EN -> innsendingsytelseToDisplayName[innsendingsytelse]!!.en
                LanguageEnum.NN -> innsendingsytelseToDisplayName[innsendingsytelse]!!.nn
            }
        )
    }
}

private fun Kode.toKodeverkDto() = KodeverkDto(id = id, navn = navn, beskrivelse = beskrivelse)

fun Kode.toKodeverkSimpleDto() = KodeverkSimpleDto(id = id, navn = navn)

private fun Kode.toEnhetKodeverkSimpleDto() = KodeverkSimpleDto(id = navn, navn = beskrivelse)

private fun Collection<Kode>.toKodeverkDto() = map { it.toKodeverkDto() }

private fun Collection<Kode>.toKodeverkSimpleDto() = map { it.toKodeverkSimpleDto() }

private fun Collection<Kode>.toEnhetKodeverkSimpleDto() = map { it.toEnhetKodeverkSimpleDto() }



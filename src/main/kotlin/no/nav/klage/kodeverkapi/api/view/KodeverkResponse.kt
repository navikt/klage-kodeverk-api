package no.nav.klage.kodeverkapi.api.view

data class KodeverkResponse(
    val ytelser: List<YtelseKode>,
    val tema: List<KodeverkDto>,
    val hjemler: List<KodeverkWithDeprecatedDto>,
    val utfall: List<KodeverkSimpleDto>,
    val enheter: List<KodeverkSimpleDto>,
    val vedtaksenheter: List<KodeverkSimpleDto>,
    val klageenheter: List<KlageenhetKode>,
    val styringsenheter: List<KodeverkSimpleDto>,
    val sakstyper: List<KodeverkSimpleDto>,
    val sakstyperToUtfall: List<TypeToUtfallKode>,
    val sources: List<KodeverkSimpleDto>,
    val brevmottakertyper: List<KodeverkSimpleDto>,
)

data class KodeverkSimpleDto(val id: String, val navn: String)

data class KodeverkWithUtfasesDto(val id: String, val navn: String, val beskrivelse: String, val utfases: Boolean)

data class KodeverkWithDeprecatedDto(val id: String, val navn: String, val beskrivelse: String, val deprecated: Boolean)

data class KodeverkDto(val id: String, val navn: String, val beskrivelse: String)

data class KodeverkFagsystemDto(val id: String, val navn: String, val beskrivelse: String, val modernized: Boolean)

data class LovKildeAndRegistreringshjemler(val lovkilde: KodeverkDto, val registreringshjemler: List<KodeverkSimpleDto>)

data class LovKildeAndHjemler(val lovkilde: KodeverkDto, val hjemler: List<KodeverkSimpleDto>)

data class LovKildeAndHjemmelnavn(
    val lovkilde: KodeverkDto,
    val hjemmelnavn: String,
)

data class LovKildeToRegistreringshjemler(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val registreringshjemler: List<KodeverkSimpleDto>
)

data class LovKildeToHjemler(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val hjemler: List<KodeverkSimpleDto>
)

data class KlageenhetKode(
    val id: String,
    val navn: String,
    val ytelser: List<KodeverkSimpleDto>,
)

data class YtelseKode(
    val id: String,
    val navn: String,
    val lovKildeToRegistreringshjemler: List<LovKildeAndRegistreringshjemler>,
    val lovKildeToHjemler: List<LovKildeAndHjemler>,
    val enheter: List<KodeverkSimpleDto>,
    val klageenheter: List<KodeverkSimpleDto>,
    val innsendingshjemler: List<KodeverkWithUtfasesDto>,
)

data class KabalytelseKode(
    val id: String,
    val navn: String,
    val lovKildeToRegistreringshjemler: List<LovKildeToRegistreringshjemler>,
    val lovKildeToHjemler: List<LovKildeToHjemler>,
)

data class TypeToUtfallKode(
    val id: String,
    val navn: String,
    val utfall: List<KodeverkSimpleDto>,
)

data class TypeToSattPaaVentReasons(
    val id: String,
    val navn: String,
    val sattPaaVentReasons: List<KodeverkDto>,
)




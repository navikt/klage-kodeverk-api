package no.nav.klage.kodeverkapi.api.view

data class KodeverkResponse(
    val ytelser: List<YtelseKode>,
    val tema: List<KodeverkDto>,
    val hjemler: List<KodeverkDto>,
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

data class KodeverkDto(val id: String, val navn: String, val beskrivelse: String)

data class LovKildeAndRegistreringshjemler(val lovkilde: KodeverkDto, val registreringshjemler: List<KodeverkSimpleDto>)

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

data class KlageenhetKode(
    val id: String,
    val navn: String,
    val ytelser: List<KodeverkSimpleDto>,
)

data class YtelseKode(
    val id: String,
    val navn: String,
    val lovKildeToRegistreringshjemler: List<LovKildeAndRegistreringshjemler>,
    val enheter: List<KodeverkSimpleDto>,
    val klageenheter: List<KodeverkSimpleDto>,
    val innsendingshjemler: List<KodeverkDto>,
)

data class TypeToUtfallKode(
    val id: String,
    val navn: String,
    val utfall: List<KodeverkSimpleDto>,
)




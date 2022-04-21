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
    val sources: List<KodeverkSimpleDto>,
)

data class KodeverkSimpleDto(val id: String, val navn: String)

data class KodeverkDto(val id: String, val navn: String, val beskrivelse: String)

data class LovKildeToRegistreringshjemler(val lovkilde: KodeverkDto, val registreringshjemler: List<KodeverkSimpleDto>)

data class KlageenhetKode(
    val id: String,
    val navn: String,
    val ytelser: List<KodeverkSimpleDto>,
)

data class YtelseKode(
    val id: String,
    val navn: String,
    val lovKildeToRegistreringshjemler: List<LovKildeToRegistreringshjemler>,
    val enheter: List<KodeverkSimpleDto>,
    val klageenheter: List<KodeverkSimpleDto>,
    val innsendingshjemler: List<KodeverkDto>,
)




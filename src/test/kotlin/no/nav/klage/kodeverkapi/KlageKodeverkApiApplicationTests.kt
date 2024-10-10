package no.nav.klage.kodeverkapi

import no.nav.klage.kodeverk.Type
import no.nav.klage.kodeverk.Utfall
import no.nav.klage.kodeverkapi.api.view.TypeToUtfallKode
import no.nav.klage.kodeverkapi.util.getTypeMap
import no.nav.klage.kodeverkapi.util.toKodeverkSimpleDto
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class KlageKodeverkApiApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun utfallSort() {
		val output = getTypeMap()
		val wantedResult = listOf(
			TypeToUtfallKode(
				id = Type.KLAGE.id,
				navn = Type.KLAGE.navn,
				utfall = listOf(
					Utfall.TRUKKET.toKodeverkSimpleDto(),
					Utfall.OPPHEVET.toKodeverkSimpleDto(),
					Utfall.MEDHOLD.toKodeverkSimpleDto(),
					Utfall.DELVIS_MEDHOLD.toKodeverkSimpleDto(),
					Utfall.STADFESTELSE.toKodeverkSimpleDto(),
					Utfall.AVVIST.toKodeverkSimpleDto(),
					Utfall.RETUR.toKodeverkSimpleDto(),
				),
			),
			TypeToUtfallKode(
				id = Type.ANKE.id,
				navn = Type.ANKE.navn,
				utfall = listOf(
					Utfall.TRUKKET.toKodeverkSimpleDto(),
					Utfall.OPPHEVET.toKodeverkSimpleDto(),
					Utfall.MEDHOLD.toKodeverkSimpleDto(),
					Utfall.DELVIS_MEDHOLD.toKodeverkSimpleDto(),
					Utfall.INNSTILLING_STADFESTELSE.toKodeverkSimpleDto(),
					Utfall.INNSTILLING_AVVIST.toKodeverkSimpleDto(),
				),
			),
			TypeToUtfallKode(
				id = Type.ANKE_I_TRYGDERETTEN.id,
				navn = Type.ANKE_I_TRYGDERETTEN.navn,
				utfall = listOf(
					Utfall.OPPHEVET.toKodeverkSimpleDto(),
					Utfall.MEDHOLD.toKodeverkSimpleDto(),
					Utfall.DELVIS_MEDHOLD.toKodeverkSimpleDto(),
					Utfall.STADFESTELSE.toKodeverkSimpleDto(),
					Utfall.AVVIST.toKodeverkSimpleDto(),
					Utfall.HEVET.toKodeverkSimpleDto(),
					Utfall.HENVIST.toKodeverkSimpleDto(),
				),
			),
			TypeToUtfallKode(
				id = Type.BEHANDLING_ETTER_TRYGDERETTEN_OPPHEVET.id,
				navn = Type.BEHANDLING_ETTER_TRYGDERETTEN_OPPHEVET.navn,
				utfall = listOf(
					Utfall.TRUKKET.toKodeverkSimpleDto(),
					Utfall.OPPHEVET.toKodeverkSimpleDto(),
					Utfall.MEDHOLD.toKodeverkSimpleDto(),
					Utfall.DELVIS_MEDHOLD.toKodeverkSimpleDto(),
					Utfall.STADFESTELSE.toKodeverkSimpleDto(),
					Utfall.AVVIST.toKodeverkSimpleDto(),
					Utfall.RETUR.toKodeverkSimpleDto(),
				),
			),
		)
		assertThat(output).isEqualTo(wantedResult)
	}
}

package no.nav.klage.kodeverkapi

import no.nav.klage.kodeverk.Type
import no.nav.klage.kodeverk.Utfall
import no.nav.klage.kodeverkapi.api.view.TypeToUtfallKode
import no.nav.klage.kodeverkapi.util.*
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
					Utfall.UGUNST.toKodeverkSimpleDto(),
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
		)
		assertThat(output.find { it.id == Type.KLAGE.id }).isEqualTo(wantedResult.find { it.id == Type.KLAGE.id })
		assertThat(output.find { it.id == Type.ANKE.id }).isEqualTo(wantedResult.find { it.id == Type.ANKE.id })
	}

	@Test
	fun stringComparatorRespectingNumeralsTest() {
		assertThat(stringComparatorRespectingNumerals.compare("1", "1-8")).isLessThan(0)
		assertThat(stringComparatorRespectingNumerals.compare("1-8", "1")).isGreaterThan(0)
		assertThat(stringComparatorRespectingNumerals.compare("5", "2")).isGreaterThan(0)
		assertThat(stringComparatorRespectingNumerals.compare("1", "1")).isEqualTo(0)

		val string1 = "§ 1-9"
		val string2 = "§ 2-11"
		val string3 = "§ 23-10"
		val string4 = "§ 2-10"
		val string5 = "§ 2-10c"
		val string6 = "§ 2-10a"
		val string7 = "§ 2-10b"
		val string8 = "§ 1"
		val stringList = listOf(string1, string2, string3, string4, string5, string6, string7, string8)
		val output = stringList.sortedWith(stringComparatorRespectingNumerals)
		assertThat(output).isEqualTo(listOf(string8, string1, string4, string6, string7, string5, string2, string3))
	}
}

package no.nav.klage.kodeverkapi

import no.nav.klage.kodeverk.Type
import no.nav.klage.kodeverk.Utfall
import no.nav.klage.kodeverkapi.api.view.TypeToUtfallKode
import no.nav.klage.kodeverkapi.util.getTypeToUtfallMap
import no.nav.klage.kodeverkapi.util.hjemmelComparator
import no.nav.klage.kodeverkapi.util.toKodeverkSimpleDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KlageKodeverkApiApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun utfallSort() {
        val output = getTypeToUtfallMap()
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
                    Utfall.HENLAGT.toKodeverkSimpleDto(),
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
                    Utfall.HENLAGT.toKodeverkSimpleDto(),
                ),
            ),
        )
        assertThat(output.find { it.id == Type.KLAGE.id }).isEqualTo(wantedResult.find { it.id == Type.KLAGE.id })
        assertThat(output.find { it.id == Type.ANKE.id }).isEqualTo(wantedResult.find { it.id == Type.ANKE.id })
    }

    @Test
    fun hjemmelComparatorTest() {
//        val l =
//            ytelseToRegistreringshjemlerV2.flatMap { it.value }.map { it.spesifikasjon }.filter { it.contains("§ 1") && (it.contains("ledd") || (it.contains("ortoser"))) }
//        val leddTest = l.sortedWith(hjemmelComparator)

        assertThat(hjemmelComparator.compare("1 andre ledd", "1 første ledd")).isGreaterThan(0)
        assertThat(hjemmelComparator.compare("1 første ledd", "1 brev ledd")).isLessThan(0)
        assertThat(hjemmelComparator.compare("1", "1-8")).isLessThan(0)
        assertThat(hjemmelComparator.compare("1-8", "1")).isGreaterThan(0)
        assertThat(hjemmelComparator.compare("5", "2")).isGreaterThan(0)
        assertThat(hjemmelComparator.compare("1", "1")).isEqualTo(0)

        val string1 = "§ 1-9"
        val string2 = "§ 2-11"
        val string3 = "§ 23-10"
        val string4 = "§ 2-10"
        val string5 = "§ 2-10c"
        val string6 = "§ 2-10a"
        val string7 = "§ 2-10b"
        val string8 = "§ 1"
        val string9 = "§ 25-1 tredje ledd"
        val string10 = "§ 25-1 første ledd"
        val string11 = "§ 25-1 andre ledd"

        val stringList = listOf(
            string1,
            string2,
            string3,
            string4,
            string5,
            string6,
            string7,
            string8,
            string9,
            string10,
            string11
        )
        val output = stringList.sortedWith(hjemmelComparator)
        assertThat(output).isEqualTo(
            listOf(
                string8,
                string1,
                string4,
                string6,
                string7,
                string5,
                string2,
                string3,
                string10,
                string11,
                string9
            )
        )
    }
}
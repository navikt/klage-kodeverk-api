package no.nav.klage.kodeverkapi.util

val hjemmelComparator = Comparator<String> { value1, value2 ->
    val firstArray = value1.toComparisonList()
    val secondArray = value2.toComparisonList()

    firstArray.zip(secondArray).forEach { (first, second) ->
        val result = numberOrOrdinalOrStringComparator.compare(first, second)
        if (result != 0) {
            return@Comparator result
        }
    }

    firstArray.size - secondArray.size
}

private fun String.toComparisonList() =
    digitRegex.findAll(input = this).mapNotNull { it.groupValues.first().takeIf { s -> s.isNotBlank() } }.toList()

private val numberOrOrdinalOrStringComparator = Comparator<String> { value1, value2 ->
    val value1PotentialInt = value1.toIntOrNull()
    if (value1PotentialInt != null) {
        val value2PotentialInt = value2.toIntOrNull()
        if (value2PotentialInt != null) {
            return@Comparator value1PotentialInt.compareTo(value2PotentialInt)
        }
    }

    val value1PotentialOrdinal = Ordinal.entries.find { it.name == value1 }
    if (value1PotentialOrdinal != null) {
        val value2PotentialOrdinal = Ordinal.entries.find { it.name == value2 }
        if (value2PotentialOrdinal != null) {
            return@Comparator value1PotentialOrdinal.compareTo(value2PotentialOrdinal)
        }
    }

    value1.compareTo(value2)
}

private val digitRegex = "\\d+|\\s+|\\p{L}+".toRegex()

private enum class Ordinal {
    første,
    andre,
    tredje,
    fjerde,
    femte,
    sjette,
    sjuende,
    syvende,
    åttende,
    niende,
    tiende,
}
package no.nav.klage.kodeverkapi.util

val stringComparatorRespectingNumerals = Comparator<String> { o1, o2 ->
    val firstArray = digitRegex.findAll(input = o1).map { it.groupValues.first() }.toList()
    val secondArray = digitRegex.findAll(input = o2).map { it.groupValues.first() }.toList()

    firstArray.forEachIndexed { index, s ->
        val result = stringWithNumeralComparator.compare(s, secondArray.getOrNull(index))
        if (result != 0) {
            return@Comparator result
        }
    }
    0
}

val stringWithNumeralComparator = Comparator<String> { s1, s2 ->
    if (s2 != null) {
        val firstInt = s1.toIntOrNull()
        val secondInt = s2.toIntOrNull()
        if (firstInt != null && secondInt != null) {
            firstInt.compareTo(secondInt)
        } else {
            s1.compareTo(s2)
        }
    } else {
        1
    }
}

val digitRegex = "\\d+|\\D+".toRegex()

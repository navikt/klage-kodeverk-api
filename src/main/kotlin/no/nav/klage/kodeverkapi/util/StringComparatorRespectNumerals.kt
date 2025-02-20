package no.nav.klage.kodeverkapi.util

val stringComparatorRespectingNumerals = Comparator<String> { o1, o2 ->
    val firstArray = digitRegex.findAll(input = o1).map { it.groupValues.first() }.toList()
    val secondArray = digitRegex.findAll(input = o2).map { it.groupValues.first() }.toList()

    firstArray.zip(secondArray).forEach { (first, second) ->
        val result = stringWithNumeralComparator.compare(first, second)
        if (result != 0) {
            return@Comparator result
        }
    }

    firstArray.size - secondArray.size
}

val stringWithNumeralComparator = Comparator<String> { s1, s2 ->
    val firstInt = s1.toIntOrNull()
    val secondInt = s2.toIntOrNull()
    if (firstInt != null && secondInt != null) {
        firstInt.compareTo(secondInt)
    } else {
        s1.compareTo(s2)
    }
}

val digitRegex = "\\d+|\\D+".toRegex()

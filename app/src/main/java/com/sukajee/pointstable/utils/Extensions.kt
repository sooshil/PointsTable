package com.sukajee.pointstable.utils

import java.util.Locale

typealias NumberOfTeams = Int

fun NumberOfTeams.getNumberOfMatches(roundRobinTimes: Int): Int {
    return if (this < 2) 0
    else roundRobinTimes * this * (this - 1) / 2
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    it.lowercase().replaceFirstChar { char ->
        char.titlecase(Locale.getDefault())
    }
}
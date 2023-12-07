package com.sukajee.pointstable.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.sukajee.pointstable.data.model.Series
import java.util.Locale

typealias NumberOfTeams = Int
typealias GameName = String

fun NumberOfTeams.getNumberOfMatches(roundRobinTimes: Int): Int {
    return if (this < 2) 0
    else roundRobinTimes * this * (this - 1) / 2
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    it.lowercase().replaceFirstChar { char ->
        char.titlecase(Locale.getDefault())
    }
}

fun String.capitalizeFirstLetter(): String = this.replaceFirstChar { char ->
    char.titlecase(Locale.getDefault())
}

fun GameName.getFirstTeam() = this.substringBefore(" vs ")

fun GameName.getSecondTeam() = this.substringAfter(" vs ")

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Series.generateMatchNames(): List<String> {
    val result = mutableListOf<String>()
    for (i in 0 until improvedTeams.size - 1) {
        for (j in i + 1 until improvedTeams.size) {
            for (k in 1..roundRobinTimes) {
                result.add("${improvedTeams[i]} vs ${improvedTeams[j]}")
            }
        }
    }
    return result
}

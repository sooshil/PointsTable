/*
 * Copyright (c) 2023-2024, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Monday, 01 Jan, 2024.
 */

package com.sukajee.pointstable.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.sukajee.pointstable.data.model.Series
import java.util.Locale
import kotlin.math.round

typealias NumberOfTeams = Int
typealias GameName = String
typealias EditDisabledSeriesIds = String
typealias DecimalOvers = Double

fun NumberOfTeams.getNumberOfMatches(roundRobinTimes: Int): Int {
    return if (this < 2) 0
    else roundRobinTimes * this * (this - 1) / 2
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    if (it.length > 1 && it[0].isUpperCase() && it[1].isUpperCase()) {
        it
    } else {
        it.lowercase().replaceFirstChar { char ->
            char.titlecase(Locale.getDefault())
        }
    }
}

fun String.capitalizeFirstLetter(): String = this.replaceFirstChar { char ->
    char.titlecase(Locale.getDefault())
}

fun EditDisabledSeriesIds.insertSeriesId(seriesId: String): String {
    return if (this.split(",").map { it.trim() }.contains(seriesId)) this
    else this.split(",").map {
        it.trim()
    }.toMutableList().apply {
        add(seriesId.trim())
    }.joinToString(",")
}

fun EditDisabledSeriesIds.containsSeriesId(seriesId: String): Boolean {
    return this.split(",").map { it.trim() }.contains(seriesId.trim())
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

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun DecimalOvers.toOversAndBalls(): String {
    val overs = this.toInt()
    val balls = ((this - overs) * 10).toInt()
    return "$overs.$balls"
}

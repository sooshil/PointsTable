package com.sukajee.pointstable.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
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

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

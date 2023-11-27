package com.sukajee.pointstable.utils

fun Int.getNumberOfMatches(roundRobinTimes: Int): Int {
    return if (this < 2) 0
    else roundRobinTimes * this * (this - 1) / 2
}
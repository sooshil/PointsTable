package com.sukajee.pointstable.utils

typealias NumberOfTeams = Int

fun NumberOfTeams.getNumberOfMatches(roundRobinTimes: Int): Int {
    return if (this < 2) 0
    else roundRobinTimes * this * (this - 1) / 2
}
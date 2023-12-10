package com.sukajee.pointstable.data.model

data class PointTableRow(
    val teamName: String,
    val played: Int,
    val won: Int,
    val lost: Int,
    val drawn: Int,
    val noResult: Int,
    val points: Int,
    val netRunRate: Double
)

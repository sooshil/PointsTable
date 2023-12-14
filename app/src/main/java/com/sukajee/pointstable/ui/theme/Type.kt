package com.sukajee.pointstable.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sukajee.pointstable.R

val GafataNumerical = FontFamily(
    Font(R.font.gafata, weight = FontWeight.Normal)
)

val Sarabun = FontFamily(
    Font(R.font.sarabun_regular, weight = FontWeight.Normal),
    Font(R.font.sarabun_bold, weight = FontWeight.Bold),
    Font(R.font.sarabun_light, weight = FontWeight.Light),
    Font(R.font.sarabun_semibold, weight = FontWeight.SemiBold),
    Font(R.font.sarabun_medium, weight = FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Sarabun,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.3.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
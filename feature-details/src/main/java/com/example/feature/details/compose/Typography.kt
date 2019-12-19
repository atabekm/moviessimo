package com.example.feature.details.compose

import androidx.ui.core.sp
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTypography
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight

val themeTypography = MaterialTypography(
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        color = Color.Black
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Gray
    )
)

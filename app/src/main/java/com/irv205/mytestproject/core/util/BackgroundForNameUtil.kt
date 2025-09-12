package com.irv205.mytestproject.core.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

// Paleta cíclica tipo “pastel”. Selección estable por nombre.
@Stable
fun backgroundForName(name: String): Color {
    val palette = listOf(
        Color(0xFF2C7A7B), // teal
        Color(0xFFDD6B20), // orange
        Color(0xFF805AD5), // purple
        Color(0xFF2B6CB0), // blue
        Color(0xFF319795), // cyan
        Color(0xFF38A169), // green
        Color(0xFFB83280), // pink
        Color(0xFFDD6B20), // orange (again for variedad)
        Color(0xFF718096)  // gray
    )
    val idx = kotlin.math.abs(name.lowercase().hashCode()) % palette.size
    return palette[idx]
}
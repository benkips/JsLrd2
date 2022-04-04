package com.mabnets.jslradio.components

import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

data class CascadeMenuColors(
    val backgroundColor: Color,
    val contentColor: Color
)

@Composable
fun cascadeMenuColors(
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface
): CascadeMenuColors {
    return CascadeMenuColors(backgroundColor, contentColor)
}
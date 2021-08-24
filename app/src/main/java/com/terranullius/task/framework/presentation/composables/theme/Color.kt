package com.terranullius.task.framework.presentation.composables.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable fun getTextColor() = if(isSystemInDarkTheme()) Color.Gray else MaterialTheme.colors.onBackground

@Composable fun getHeadlineTextColor() = if(isSystemInDarkTheme()) Color.Gray else MaterialTheme.colors.onBackground
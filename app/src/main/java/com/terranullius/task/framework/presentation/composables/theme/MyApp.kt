package com.terranullius.task.framework.presentation.composables.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.terranullius.task.framework.presentation.composables.Navigation

@Composable
fun MyApp(modifier: Modifier = Modifier.fillMaxSize()) {

    TaskTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            Navigation()
        }
    }
}
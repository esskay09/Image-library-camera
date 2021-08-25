package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.theme.TaskTheme
import com.terranullius.task.framework.presentation.composables.theme.mainPadding


/**
 * App theme and navigation applied to and accessible by all screens
 * */

@Composable
fun MyApp(
    modifier: Modifier = Modifier.fillMaxSize().padding(horizontal = mainPadding),
    viewModel: MainViewModel
) {

    TaskTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            Navigation(viewModel = viewModel, modifier = modifier)
        }
    }
}
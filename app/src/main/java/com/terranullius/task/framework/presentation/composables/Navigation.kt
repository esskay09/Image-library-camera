package com.terranullius.task.framework.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.util.Screen


/**
 * Set up Navigation
 * */

@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
    ){

        composable(Screen.Main.route){
            MainScreen(navController = navController, viewModel = viewModel, modifier = modifier)
        }

        composable(Screen.ImageDetail.route){
            ImageDetailScreen(viewModel = viewModel, modifier = modifier)
        }

    }

}
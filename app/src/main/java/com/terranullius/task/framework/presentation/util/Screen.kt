package com.terranullius.task.framework.presentation.util

sealed class Screen(val route: String){

    object Main: Screen("main-screen")
    object ImageDetail: Screen("detail-screen")

}
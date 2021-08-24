package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.components.LoadingComposable
import com.terranullius.task.framework.presentation.composables.theme.spaceBetweenImages

@Composable
fun MainScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
            ) {
                Text(text = "Images")
            }
        }
    ) {
        val imageStateFlow = viewModel.imageStateFlow.collectAsState()
        MainScreenContent(
            Modifier
                .fillMaxSize()
                .padding(it),
            navController,
            imageStateFlow
        )
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    imageStateFlow: State<StateResource<List<Image>>>
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (imageStateFlow.value) {
            is StateResource.Loading -> {
                LoadingComposable()
            }
            is StateResource.Error -> {
                val errorMsg = (imageStateFlow.value as StateResource.Error).message
                ErrorComposable(msg = errorMsg)
            }
            is StateResource.Success -> {
                val imageList = (imageStateFlow.value as StateResource.Success<List<Image>>).data

                ImageList(
                    modifier = Modifier.fillMaxSize(),
                    images = imageList
                )
            }
        }
    }
}

@Composable
fun ImageList(
    modifier: Modifier = Modifier,
    images: List<Image>
) {

    LazyColumn(modifier = modifier) {
        itemsIndexed(images) { index: Int, item: Image ->

            //TODO
            /*val screenHeight = LocalConfiguration.current.screenHeightDp
            val imageHeight = with(LocalDensity.current) {
                screenHeight.div(3).toDp()
            }*/


            Column() {
                ImageCard(
                    image = item,
                    modifier = Modifier.height(200.dp),
                    onClick = {
                        //TODO
                    }
                )
                Spacer(modifier = Modifier.height(spaceBetweenImages))
            }
        }
    }
}
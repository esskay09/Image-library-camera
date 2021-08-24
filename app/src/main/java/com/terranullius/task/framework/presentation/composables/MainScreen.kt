package com.terranullius.task.framework.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.terranullius.task.framework.presentation.util.Screen

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
            imageStateFlow
        ) {
            setImageSelected(it, viewModel)
            navigateImageDetail(navController)
        }
    }
}

fun navigateImageDetail(navController: NavHostController) {
    navController.navigate(Screen.ImageDetail.route)
}

fun setImageSelected(image: Image, viewModel: MainViewModel) {
    viewModel.setSelectedImage(image)
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    imageStateFlow: State<StateResource<List<Image>>>,
    onCardClick: (Image) -> Unit
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
                ) {
                    onCardClick(it)
                }
            }
        }
    }
}

@Composable
fun ImageList(
    modifier: Modifier = Modifier,
    images: List<Image>,
    onCardClick: (Image) -> Unit
) {

    LaunchedEffect(key1 = Unit){
        images.forEach {
            Log.d("testImage", it.imageUrl)
        }
    }

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
                        onCardClick(it)
                    }
                ){ image->
                    Text(
                        text = image.title,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = 4.dp, y = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(spaceBetweenImages))
            }
        }
    }
}
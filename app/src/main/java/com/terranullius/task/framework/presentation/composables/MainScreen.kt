package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.components.LoadingComposable
import com.terranullius.task.framework.presentation.composables.theme.spaceBetweenImages
import com.terranullius.task.framework.presentation.composables.theme.textColor
import com.terranullius.task.framework.presentation.composables.util.ListType
import com.terranullius.task.framework.presentation.util.Screen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val imageHeight = screenHeight.div(3.3).dp

    var listType by rememberSaveable {
        mutableStateOf(ListType.LINEAR)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Images")
                },
                actions = {
                    IconButton(onClick = {
                        listType =
                            if (listType == ListType.LINEAR) ListType.GRID else ListType.LINEAR
                    }) {
                        Icon(
                            imageVector = if (listType == ListType.GRID) Icons.Default.List else Icons.Default.GridView,
                            contentDescription = "list",
                            tint = textColor
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val imageStateFlow = viewModel.imageStateFlow.collectAsState()
        MainScreenContent(
            modifier = modifier.padding(paddingValues),
            imageStateFlow,
            listType,
            imageHeight
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
    listType: ListType,
    imageHeight: Dp,
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
                    images = imageList,
                    listType = listType,
                    imageHeight = imageHeight
                ) {
                    onCardClick(it)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ImageList(
    modifier: Modifier = Modifier,
    images: List<Image>,
    listType: ListType,
    imageHeight: Dp,
    onCardClick: (Image) -> Unit,
) {
    LazyColumn(modifier = modifier) {

        when (listType) {
            ListType.LINEAR -> itemsIndexed(images) { _: Int, item: Image ->
                Column(Modifier.padding(vertical = spaceBetweenImages)) {
                    ImageCard(
                        image = item,
                        modifier = Modifier.height(imageHeight.div(0.9f)),
                        onClick = {
                            onCardClick(it)
                        }
                    ) { image ->
                        Text(
                            text = image.title,
                            color = textColor,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .offset(x = 4.dp, y = (-4).dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(spaceBetweenImages))
                }
            }

            ListType.GRID -> {

                val chunkedList = images.chunked(2)

                itemsIndexed(chunkedList) { _: Int, chunkedItem: List<Image> ->

                    Row(Modifier.fillMaxWidth()) {
                        chunkedItem.forEach {

                            Column(Modifier.weight(1f).padding(spaceBetweenImages)) {
                                ImageCard(
                                    image = it,
                                    modifier = Modifier.height(imageHeight),
                                    onClick = {
                                        onCardClick(it)
                                    }
                                ) { image ->
                                    Text(
                                        text = image.title,
                                        color = textColor,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .offset(x = 4.dp, y = (-4).dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(spaceBetweenImages))
                            }
                        }
                    }
                }

            }
        }


    }
}
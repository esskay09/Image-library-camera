package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var listType by remember {
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
                            imageVector = if (listType == ListType.LINEAR) Icons.Default.List else Icons.Default.GridView,
                            contentDescription = "list"
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
            listType
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
                    listType = listType
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
    onCardClick: (Image) -> Unit,
) {

    LazyColumn(modifier = modifier) {

        //TODO
        /*val screenHeight = LocalConfiguration.current.screenHeightDp
        val imageHeight = with(LocalDensity.current) {
            screenHeight.div(3).toDp()
        }*/

        when (listType) {
            ListType.LINEAR -> itemsIndexed(images) { _: Int, item: Image ->
                Column() {
                    ImageCard(
                        image = item,
                        modifier = Modifier.height(200.dp),
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

                itemsIndexed(chunkedList) { index: Int, chunkedItem: List<Image> ->

                    Row(Modifier.fillMaxWidth()) {
                        chunkedItem.forEach {

                            Column(Modifier.weight(1f)) {
                                ImageCard(
                                    image = it,
                                    modifier = Modifier.height(200.dp),
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
package com.terranullius.task.framework.presentation.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.theme.*
import com.terranullius.task.framework.presentation.util.StateEvent

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel,
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val imageHeight = screenHeight.div(2.4).dp

    val selectedImage = remember {
        viewModel.selectedImage
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (selectedImage.value) {
            null -> {
                ErrorComposable()
            }
            else -> {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            viewModel.setStateEvent(StateEvent.ShareImage(selectedImage.value!!.imageUrl))
                        }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                        }
                    }, floatingActionButtonPosition = FabPosition.End
                ) { paddingValues ->
                    ImageDetailContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = paddingValues
                                    .calculateTopPadding()
                                    .plus(8.dp),
                                bottom = paddingValues.calculateBottomPadding(),
                                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                            ),
                        image = selectedImage.value!!,
                        imageHeight = imageHeight
                    )
                }
            }
        }
    }
}

@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier,
    image: Image,
    imageHeight: Dp,
) {
    when (LocalConfiguration.current.orientation) {
        ORIENTATION_LANDSCAPE -> ImageDetailContentLandScape(modifier = modifier, image = image, imageHeight = imageHeight)
        ORIENTATION_PORTRAIT -> ImageDetailContentPotrait(modifier = modifier, image = image, imageHeight = imageHeight)
        else -> ImageDetailContentPotrait(
            modifier = modifier,
            image = image,
            imageHeight = imageHeight
        )
    }
}

@Composable
private fun ImageDetailDescription(image: Image, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item{
            Spacer(modifier = Modifier.height(12.dp))
        }

        item{
            Row {
                Text(
                    text = image.title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold,
                        color = getHeadlineTextColor()
                    )
                )
            }
        }
        item{
            Spacer(modifier = Modifier.height(18.dp))
        }

        item{
            Text(text = image.description, color = getTextColor())
        }

        item{
            Spacer(modifier = Modifier.height(24.dp))
        }

        item{
            Box(Modifier.fillMaxWidth()){
                Row(Modifier.align(Alignment.TopEnd)) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "", tint = getTextColor())
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = image.publishedDate.substringBefore("T"), color = getTextColor())
                }
            }
        }
    }
}


@Composable
private fun ImageDetailContentPotrait(
    modifier: Modifier,
    image: Image,
    imageHeight: Dp
) {
    Column(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
        ) {
        }

        ImageDetailDescription(image)
    }
}

@Composable
fun ImageDetailContentLandScape(
    modifier: Modifier = Modifier,
    image: Image,
    imageHeight: Dp
) {
    Row(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
        }

        ImageDetailDescription(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 8.dp), image = image
        )
    }

}
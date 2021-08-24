package com.terranullius.task.framework.presentation.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.theme.imageDetailHeight
import com.terranullius.task.framework.presentation.composables.theme.textColor
import com.terranullius.task.framework.presentation.composables.theme.textHeadlineColor

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel,
) {

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
                            viewModel.onShare(selectedImage.value!!.imageUrl)
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
                        image = selectedImage.value!!
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
) {
    when (LocalConfiguration.current.orientation) {
        ORIENTATION_LANDSCAPE -> ImageDetailContentLandScape(modifier = modifier, image = image)
        ORIENTATION_PORTRAIT -> ImageDetailContentPotrait(modifier = modifier, image = image)
        else -> ImageDetailContentPotrait(modifier = modifier, image = image)
    }
}

@Composable
private fun ImageDetailDescription(image: Image, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Text(
                text = image.title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = textHeadlineColor
                )
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text(text = image.description, color = textColor)

        Spacer(modifier = Modifier.height(36.dp))

        Row(Modifier.align(Alignment.End)) {
            Icon(Icons.Default.CalendarToday, contentDescription = "", tint = textColor)
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = image.publishedDate.substringBefore("T"), color = textColor)
        }
    }
}


@Composable
private fun ImageDetailContentPotrait(
    modifier: Modifier,
    image: Image
) {
    Column(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(imageDetailHeight)
        ) {
        }

        ImageDetailDescription(image)
    }
}

@Composable
fun ImageDetailContentLandScape(
    modifier: Modifier = Modifier,
    image: Image
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
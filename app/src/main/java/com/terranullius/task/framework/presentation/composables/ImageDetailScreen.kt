package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.theme.imageDetailHeight
import com.terranullius.task.framework.presentation.composables.theme.textColor

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel
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
                ImageDetailContent(
                    modifier = Modifier.fillMaxSize(),
                    image = selectedImage.value!!
                )
            }
        }
    }
}

@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier,
    image: Image
) {
    Column(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(imageDetailHeight)
        ){
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(text = image.title, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(18.dp))
        
        Text(text = image.description, color = textColor)
        
    }

}
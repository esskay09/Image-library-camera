package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.theme.imageDetailHeight
import com.terranullius.task.framework.presentation.composables.theme.textColor
import com.terranullius.task.framework.presentation.composables.theme.textHeadlineColor
import java.text.SimpleDateFormat
import java.util.*

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
                Scaffold() {
                    ImageDetailContent(
                        modifier = Modifier.fillMaxSize().padding(it),
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

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = image.title,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = textHeadlineColor
            )
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text(text = image.description, color = textColor)
        
        Spacer(modifier = Modifier.height(36.dp))
        
        Row(Modifier.align(Alignment.End)) {
//            Image(painter = rememberVectorPainter(image = Icons.Default.CalendarToday), contentDescription = "")
            Icon(Icons.Default.CalendarViewMonth, contentDescription = "", tint = textColor)
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = image.publishedDate.substringBefore("T"), color = textColor)
        }

    }

}
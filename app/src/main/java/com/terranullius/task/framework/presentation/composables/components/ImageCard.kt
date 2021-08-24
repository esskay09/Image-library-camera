package com.terranullius.task.framework.presentation.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.terranullius.task.business.domain.model.Image

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Image,
    onClick: (Image) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(image)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            val painter = rememberImagePainter(data = image.imageUrl, builder = {
                crossfade(true)
            })

            Image(
                painter = painter,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "image"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
        }

    }
}
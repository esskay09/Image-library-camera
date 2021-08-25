package com.terranullius.task.business.domain.model

import androidx.compose.ui.graphics.ImageBitmap

/**
* Domain model
* */

data class Image(
    val id: String,
    val description: String,
    val imageUrl: String,
    val imageBitmap: ImageBitmap? = null,
    val publishedDate: String,
    val title: String
)
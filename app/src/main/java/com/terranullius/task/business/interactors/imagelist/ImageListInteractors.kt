package com.terranullius.task.business.interactors.imagelist

import javax.inject.Inject

// Use cases
data class ImageListInteractors @Inject constructor(
    val getAllImages: GetAllImages
)

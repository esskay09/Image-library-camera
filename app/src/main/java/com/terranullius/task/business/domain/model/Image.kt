package com.terranullius.task.business.domain.model

/**
* Domain model
* */


data class Image(
    val id: String,
    val description: String,
    val imageUrl: String,
    val publishedDate: String,
    val title: String
)
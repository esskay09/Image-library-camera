package com.terranullius.task.framework.datasource.network.model

import com.squareup.moshi.Json


/**
 * Network transfer object for images fetched from API
 * */

data class ImageDto(

    @Json(name = "_id")
    val id: String,

    @Json(name = "comment")
    val description: String,

    @Json(name = "picture")
    val imageUrl: String,

    @Json(name = "publishedAt")
    val publishedDate: String,

    @Json(name = "title")
    val title: String
)
package com.terranullius.task.framework.datasource.network.implementation

import com.terranullius.task.framework.datasource.network.model.ImageDto
import retrofit2.http.GET

interface ApiService {

    @GET("VGOL")
    suspend fun getImages() : List<ImageDto>

}
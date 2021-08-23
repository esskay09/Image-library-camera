package com.terranullius.task.business.data.network.abstraction

import com.terranullius.task.business.domain.model.Image


interface ImageNetworkDataSource {

    suspend fun getAllImages(): List<Image>

}
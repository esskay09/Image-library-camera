package com.terranullius.task.framework.datasource.network.abstraction

import com.terranullius.task.business.domain.model.Image

interface ImageNetworkService {
    suspend fun getAllImages(): List<Image>
}
package com.terranullius.task.framework.datasource.network.implementation

import com.terranullius.task.framework.datasource.network.abstraction.ImageNetworkService
import com.terranullius.task.framework.datasource.network.mappers.NetworkMapper

class ImageNetworkServiceImpl(private val networkMapper: NetworkMapper) : ImageNetworkService {

    override suspend fun getAllImages() {
        TODO("Not yet implemented")
    }
}
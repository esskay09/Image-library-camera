package com.terranullius.task.framework.datasource.network.abstraction

interface ImageNetworkService {
    suspend fun getAllImages()
}
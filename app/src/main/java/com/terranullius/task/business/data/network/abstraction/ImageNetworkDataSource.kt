package com.terranullius.task.business.data.network.abstraction


interface ImageNetworkDataSource {

    suspend fun getAllImages()

}
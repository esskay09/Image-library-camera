package com.terranullius.task.business.interactors.imagelist

import com.terranullius.task.business.data.network.ApiResponseHandler
import com.terranullius.task.business.data.network.NetworkErrors
import com.terranullius.task.business.data.network.abstraction.ImageNetworkDataSource
import com.terranullius.task.business.data.util.safeApiCall
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.StateResource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for getting all images from API
 * */


class GetAllImages @Inject constructor(private val imageNetworkDataSource: ImageNetworkDataSource) {

    fun getAllImages(): Flow<StateResource<List<Image>>> = flow {

        emit(StateResource.Loading)

        val apiResult = safeApiCall(IO) {
            imageNetworkDataSource.getAllImages()
        }

        val apiResponse = object : ApiResponseHandler<List<Image>>(
            apiResult
        ) {
            override suspend fun handleSuccess(resultObj: List<Image>): StateResource<List<Image>>? {
                return if (resultObj.isNotEmpty()) StateResource.Success(resultObj) else StateResource.Error(
                    message = NetworkErrors.NETWORK_DATA_NULL
                )
            }
        }.getResult()

        apiResponse?.let {
            emit(it)
        } ?: StateResource.Error(message = NetworkErrors.NETWORK_ERROR_UNKNOWN)

    }
}
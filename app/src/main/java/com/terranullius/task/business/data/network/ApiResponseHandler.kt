package com.terranullius.task.business.data.network

import com.terranullius.task.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.terranullius.task.business.data.network.NetworkErrors.NETWORK_ERROR
import com.terranullius.task.business.domain.state.StateResource

/**
 * Helper class to handle all api responses and convert them into StateResource
* */


abstract class ApiResponseHandler <Data>(
    private val response: ApiResult<Data?>
){

    suspend fun getResult(): StateResource<Data>? {

        return when(response){

            is ApiResult.GenericError -> {
                StateResource.Error(
                    message = "Reason: ${response.errorMessage.toString()}"
                )
            }

            is ApiResult.NetworkError -> {
                StateResource.Error(
                    message = "Reason: ${NETWORK_ERROR}"
                )
            }

            is ApiResult.Success -> {
                if(response.value == null){
                    StateResource.Error(
                        message = "Reason: ${NETWORK_DATA_NULL}"
                    )
                }
                else{
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): StateResource<Data>?

}
package com.terranullius.task.business.domain.state

sealed class StateResource<out R> {

    data class Success<out T>(val data: T ) : StateResource<T>()
    data class Error(val exception: Exception? = null, val message: String = "Unknown error") : StateResource<Nothing>()
    object Loading : StateResource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[data=$message]"
            Loading -> "Loading"
        }
    }
}
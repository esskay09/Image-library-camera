package com.terranullius.task.framework.presentation.util

import com.terranullius.task.business.domain.model.Image


sealed class StateEvent{
    data class ShareImage(val imgUrl: String): StateEvent()
    data class NavigateImageDetail(val image: Image): StateEvent()
    object CaptureImage: StateEvent()
    object None: StateEvent()
}

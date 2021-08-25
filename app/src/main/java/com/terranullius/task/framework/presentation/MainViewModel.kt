package com.terranullius.task.framework.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.Event
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import com.terranullius.task.framework.presentation.util.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListInteractors: ImageListInteractors
) : ViewModel() {

    private val _stateEventFlow = MutableStateFlow<Event<StateEvent>>(Event(StateEvent.None))
    val stateEventFlow: StateFlow<Event<StateEvent>>
        get() = _stateEventFlow

    private val _capturedImagesStateFlow = MutableStateFlow<List<Image>>(mutableListOf())

    private val apiImageFlow: Flow<StateResource<List<Image>>> =
        imageListInteractors.getAllImages.getAllImages()

    private val _combinedImagesStateFlow: MutableStateFlow<StateResource<List<Image>>> =
        MutableStateFlow(StateResource.Loading)
    val combinedImagesStateFlow: StateFlow<StateResource<List<Image>>>
        get() = _combinedImagesStateFlow

    private val _selectedImage = mutableStateOf<Image?>(null)
    val selectedImage: State<Image?>
        get() = _selectedImage


    init {
        viewModelScope.launch {
            combineImagesFromApiAndCamera()
        }
    }

    private suspend fun combineImagesFromApiAndCamera() {
        combine(
            apiImageFlow,
            _capturedImagesStateFlow
        ) { apiResponse: StateResource<List<Image>>, imagesFromCamera: List<Image> ->
            if (apiResponse is StateResource.Success) {
                val imagesFromApi = apiResponse.data
                val combinedImages = imagesFromCamera + imagesFromApi
                StateResource.Success(combinedImages)
            } else {
                apiResponse
            }
        }.collectLatest {
            _combinedImagesStateFlow.value = it
        }
    }


    fun setStateEvent(stateEvent: StateEvent) {
        when (stateEvent) {
            is StateEvent.CaptureImage -> onCapture(stateEvent)
            is StateEvent.ShareImage -> onShare(stateEvent)
            is StateEvent.NavigateImageDetail -> setSelectedImage(stateEvent.image)
            else -> Unit
        }
    }

    private fun onShare(stateEvent: StateEvent) {
        _stateEventFlow.value = Event(stateEvent)
    }

    private fun onCapture(stateEvent: StateEvent) {
        _stateEventFlow.value = Event(stateEvent)
    }

    private fun setSelectedImage(image: Image) {
        _selectedImage.value = image
    }

    fun addCapturedImage(bitmap: Bitmap) {

        val capturedImage = generateImage(bitmap)

        _capturedImagesStateFlow.value = _capturedImagesStateFlow.value + capturedImage
    }

    private fun generateImage(bitmap: Bitmap) = Image(
        id = UUID.randomUUID().toString(),
        description = "Captured from camera",
        imageUrl = "",
        imageBitmap = bitmap.asImageBitmap(),
        publishedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            System.currentTimeMillis()
        ),
        title = "Android"
    )
}
package com.terranullius.task.framework.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.Event
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListInteractors: ImageListInteractors
) : ViewModel() {

    private val _capturedImagesStateFlow = MutableStateFlow<List<Image>>(mutableListOf())

    private val _combinedImagesStateFlow: MutableStateFlow<StateResource<List<Image>>> =
        MutableStateFlow(StateResource.Loading)
    val combinedImagesStateFlow: StateFlow<StateResource<List<Image>>>
        get() = _combinedImagesStateFlow

    val apiImageFlow: Flow<StateResource<List<Image>>> =
        imageListInteractors.getAllImages.getAllImages()

    private val _selectedImage = mutableStateOf<Image?>(null)
    val selectedImage: State<Image?>
        get() = _selectedImage

    private val _onShare = MutableStateFlow<Event<String?>>(Event(null))
    val onShare: StateFlow<Event<String?>>
        get() = _onShare

    private val _onCapture = MutableStateFlow<Event<Unit?>>(Event(null))
    val onCapture: StateFlow<Event<Unit?>>
        get() = _onCapture

    init {
        viewModelScope.launch {
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
    }


    fun setSelectedImage(image: Image) {
        _selectedImage.value = image
    }

    fun onShare(imageUrl: String) {
        _onShare.value = Event(imageUrl)
    }

    fun onCaptureClick() {
        _onCapture.value = Event(Unit)
    }

    fun addCapturedImage(bitmap: Bitmap) {

        val capturedImage = Image(
            id = UUID.randomUUID().toString(),
            description = "Captured from camera",
            imageUrl = "",
            imageBitmap = bitmap.asImageBitmap(),
            publishedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                System.currentTimeMillis()
            ),
            title = "Android"
        )

        _capturedImagesStateFlow.value = _capturedImagesStateFlow.value + capturedImage
    }
}
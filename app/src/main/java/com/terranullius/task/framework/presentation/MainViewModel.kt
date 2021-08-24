package com.terranullius.task.framework.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.Event
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListInteractors: ImageListInteractors
) : ViewModel() {

    val imageStateFlow: StateFlow<StateResource<List<Image>>> =
        imageListInteractors.getAllImages.getAllImages().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StateResource.Loading
        )

    private val _selectedImage = mutableStateOf<Image?>(null)
    val selectedImage: State<Image?>
        get() = _selectedImage

    private val _onShare = MutableStateFlow<Event<String?>>(Event(null))
    val onShare: StateFlow<Event<String?>>
        get() = _onShare

    private val _onCapture = MutableStateFlow<Event<Unit?>>(Event(null))
    val onCapture: StateFlow<Event<Unit?>>
        get() = _onCapture

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

    }
}
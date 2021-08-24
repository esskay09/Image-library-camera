package com.terranullius.task.framework.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.terranullius.task.framework.presentation.composables.MyApp
import com.terranullius.task.framework.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var cameraLauncher: ActivityResultLauncher<Void>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            it?.let {
                viewModel.addCapturedImage(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.onShare.collect {
                    it.getContentIfNotHandled()?.let {
                        shareImage(it)
                    }
                }
            }
            launch {
                viewModel.onCapture.collect {
                    it.getContentIfNotHandled()?.let {
                        takePicture()
                    }
                }
            }
        }

        setContent {
            MyApp(viewModel = viewModel)
        }
    }

    private fun takePicture() {
        cameraLauncher.launch(null)
    }

    private fun shareImage(url: String) {

        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Sharing photo")
                putExtra(Intent.EXTRA_TEXT, url)
            }
            with(Intent.createChooser(intent, "Share photo")){
                startActivity(this)
            }
        } catch (e: Exception) {
            showToast("No app found to share")
        }
    }
}
package com.example.x_chat.presentation.image_upload_screen

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.x_chat.presentation.image_upload_screen.components.CameraPreview
import com.example.x_chat.presentation.image_upload_screen.components.ImageUploadBottomBar
import com.example.x_chat.presentation.image_upload_screen.components.ImageUploadTopBar
import com.example.x_chat.presentation.image_upload_screen.components.WhichFie
import java.io.File


data class ImageUploadScreen(
    val chatId: String = "",
    val which: WhichFie = WhichFie.CHAT,
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ImageUploadViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        ImageUploadScreenImp(chatId, viewModel, popUpScreen = {
            navigator.pop()
        }, which)
    }

}


@Composable
fun ImageUploadScreenImp(
    chatId: String = "",
    viewModel: ImageUploadViewModel,
    popUpScreen: () -> Unit,
    which: WhichFie,
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    var isLoading by remember {
        mutableStateOf(false)
    }
    val url by viewModel.url.observeAsState()
    var isClickable by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(url) {
        if (url != null) {
            isLoading = false
            if (which == WhichFie.CHAT) {
                val uri = url!!.url
                if (chatId != "") {
                    sendMessage(chatId = chatId, url = uri, viewModel = viewModel)
                }
            }
            popUpScreen()
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri = uri }
    )

    lateinit var file: File

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            val inputStream = imageUri?.let { context.contentResolver.openInputStream(it) }
            inputStream?.use { input ->
                file = File(context.cacheDir, "x_chat_app_temp.png")
                file.createNewFile()
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        ImageUploadTopBar(
            isBack = isClickable,
            onBackClick = {
                if (isClickable) {
                    isClickable = false
                    imageUri = null
                } else {
                    popUpScreen()
                }
            }, onUploadClick = {
                if (isClickable) {
                    isLoading = true
                    uploadImage(file = file, viewModel = viewModel, which = which, chatId = chatId)
                }
            })
        if (isLoading) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.padding(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            if (imageUri != null) {
                if (imageUri!!.scheme == "content") {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentScale = ContentScale.Fit
                    )
                } else if (imageUri!!.scheme == "file") {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            } else {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ImageUploadBottomBar(
            onCamFlipClick = {
                controller.cameraSelector =
                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else CameraSelector.DEFAULT_BACK_CAMERA
            },
            onCamClick = {
                if (imageUri == null) {
                    if (!isClickable) {
                        takePhoto(controller, context = context) { uri ->
                            imageUri = uri
                        }
                        isClickable = true
                    }
                }
            },
            onSelectImageClick = {
                if (imageUri == null) {
                    if (!isClickable) {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                        isClickable = true
                    }
                }
            }
        )
    }
}


private fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Uri) -> Unit
) {
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues()
    ).build()

    controller.takePicture(
        outputFileOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri ?: run {
                    return
                }
                onPhotoTaken(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
            }
        }
    )
}

private fun uploadImage(
    file: File,
    viewModel: ImageUploadViewModel,
    which: WhichFie,
    chatId: String?,
) {
    viewModel.onEvent(ImageUploadEvent.UpdateFile(file))
    when (which) {
        WhichFie.CHAT -> viewModel.onEvent(ImageUploadEvent.UploadChatImage)
        WhichFie.PROFILE -> viewModel.onEvent(ImageUploadEvent.UploadProfileImage)
        WhichFie.GROUP -> {
            chatId?.let { ImageUploadEvent.UpdateChatId(it) }?.let { viewModel.onEvent(it) }
            viewModel.onEvent(ImageUploadEvent.UploadGroupImage)
        }
    }

}


private fun sendMessage(
    chatId: String?,
    url: String,
    viewModel: ImageUploadViewModel
) {
    chatId?.let { ImageUploadEvent.UpdateChatId(it) }?.let { viewModel.onEvent(it) }
    viewModel.onEvent(ImageUploadEvent.UpdateMessage(url))
    viewModel.onEvent(ImageUploadEvent.SendMessage)

}

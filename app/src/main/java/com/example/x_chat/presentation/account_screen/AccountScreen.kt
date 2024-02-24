package com.example.x_chat.presentation.account_screen


import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.presentation.account_screen.components.AccountContentCard
import com.example.x_chat.presentation.account_screen.components.AccountImage
import com.example.x_chat.presentation.account_screen.components.AccountTopBar
import com.example.x_chat.presentation.image_upload_screen.ImageUploadScreen
import com.example.x_chat.presentation.image_upload_screen.components.WhichFie
import com.example.x_chat.presentation.sign_in_screen.SignInScreen
import com.example.x_chat.presentation.util.showToastMessage

object AccountScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: AccountViewModel = hiltViewModel()
        AccountScreenImp(
            viewModel = viewModel,
            onBackClick = {
                navigator.pop()
            },
            isLogOut = {
                navigator.push(SignInScreen)
            },
            navigateToImageUploadScreen = {
                navigator.push(ImageUploadScreen(which = WhichFie.PROFILE))
            }
        )
    }
}

@Composable
fun AccountScreenImp(
    viewModel: AccountViewModel,
    onBackClick: () -> Unit,
    isLogOut: () -> Unit,
    navigateToImageUploadScreen: () -> Unit
) {
    val context = LocalContext.current as Activity
    val userState by viewModel.userModel.collectAsState(initial = null)
    val aboutField = remember {
        mutableStateOf("")
    }
    val label =
        if (userState?.about == "" || userState?.about == null) "Empty" else userState?.about
    val imageUrl =
        if (userState?.avatar == "") "" else userState?.avatar
    val isEdit = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(userState) {
        if (userState == null) {
            viewModel.onEvent(AccountEvent.FetchMe)
        }
    }
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        AccountTopBar(onBackClick = { onBackClick() })
        AccountImage(
            onImageClick = { viewModel.onEvent(AccountEvent.ClearData);navigateToImageUploadScreen(); },
            imageUrl = imageUrl
        )
        userState?.let {
            if (label != null) {
                AccountContentCard(
                    isEdit = isEdit.value,
                    user = it,
                    textState = aboutField,
                    label = label,
                    isAboutUpdate = {
                        if (isEdit.value) {
                            val about = aboutField.value
                            updateAbout(about = about, viewModel = viewModel, context = context)
                            isEdit.value = !isEdit.value
                        } else {
                            isEdit.value = !isEdit.value
                        }
                    },
                    isLogOut = {
                        viewModel.onEvent(AccountEvent.Logout)
                        isLogOut()
                    })
            }
        }
    }
}

private fun updateAbout(
    about: String,
    context: Activity,
    viewModel: AccountViewModel,
) {
    if (about == "") {
        showToastMessage("About Should not Empty", context)
        return
    }
    viewModel.onEvent(AccountEvent.UpdateAbout(about))
    viewModel.onEvent(AccountEvent.UploadAbout)
}

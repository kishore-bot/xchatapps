package com.example.x_chat.presentation.p_chat_info_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.presentation.account_screen.components.AccountImage
import com.example.x_chat.presentation.account_screen.components.Content
import com.example.x_chat.presentation.p_chat_info_screen.components.PChatTopBar

class PChatInfoScreen(
    val userId: String,
    val chatId: String,
) : Screen {
    @Composable
    override fun Content() {
        val chatInfo = ChatInfo(
            userId = userId,
            chatId = chatId
        )
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: PChatViewModel = hiltViewModel()
        PChatInfoScreenImp(
            viewModel = viewModel,
            onBackClick = {
                navigator.pop()
            },
            chatInfo = chatInfo
        )
    }

}

@Composable
fun PChatInfoScreenImp(
    chatInfo: ChatInfo,
    onBackClick: () -> Unit,
    viewModel: PChatViewModel
) {
    val userState by viewModel.userModel.collectAsState(initial = null)

    LaunchedEffect(userState) {
        if (userState == null) {
            viewModel.fetch(chatInfo)
            return@LaunchedEffect
        }
    }
    val about =
        if (userState?.about == "" || userState?.about == null) "Empty" else userState?.about
    val imageUrl =
        if (userState?.avatar == "") "" else userState?.avatar
    Column(
        modifier = Modifier.statusBarsPadding().fillMaxSize()
    ) {
        PChatTopBar {
            onBackClick()
        }
        Spacer(modifier = Modifier.height(10.dp))
        AccountImage(onImageClick = { /*TODO*/ }, isIcon = false, imageUrl = imageUrl)
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.padding(5.dp)){
            Column {
                userState?.let { Content(label = "Name : ", value = it.username) }
                userState?.let { Content(label = "Email : ", value = it.email) }
                userState?.let { Content(label = "Phone No : ", value = it.phoneNo.toString()) }
                userState?.createAt?.let { Content(label = "Created At : ", value = it.substring(0, 10)) }
                if (about != null) {
                    Content(label = "About : ", value = about)
                }
            }
        }
    }
}

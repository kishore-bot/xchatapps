package com.example.x_chat.presentation.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.domain.model.socket.receive.HomeChatModel
import com.example.x_chat.presentation.account_screen.AccountScreen
import com.example.x_chat.presentation.chat_screen.ChatScreen
import com.example.x_chat.presentation.create_chat_screen.CreateChatScreen
import com.example.x_chat.presentation.home_screen.components.HomeChatCard
import com.example.x_chat.presentation.home_screen.components.HomeFAB
import com.example.x_chat.presentation.home_screen.components.HomeTopAppBar
import com.example.x_chat.presentation.in_app_notification_screen.InAppNotificationScreen
import com.example.x_chat.presentation.search_screen.SearchScreen

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: HomeScreenViewModel = hiltViewModel()
        HomeScreenImp(viewModel, navigateToChatScreen = { homeChat ->
            navigator.push(ChatScreen(homeChat))
        },
            onAccountClick = {
                navigator.push(AccountScreen)
            }, onSearchClick = {
                navigator.push(SearchScreen)
            }, onFabClick = {
                navigator.push(CreateChatScreen)
            }, onNotiClick = { navigator.push(InAppNotificationScreen)})
    }
}

@Composable
fun HomeScreenImp(
    viewModel: HomeScreenViewModel,
    navigateToChatScreen: (HomeChatModel) -> Unit,
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit,
    onFabClick: () -> Unit,
    onNotiClick: () -> Unit
) {
    val chatMessages by viewModel.chatMessages.observeAsState()
    LaunchedEffect(chatMessages) {
        viewModel.getHomeScreenChat()
    }
    Scaffold(
        topBar = {
            HomeTopAppBar(
                onSearchClick = onSearchClick,
                onAccountClick = onAccountClick,
                onNotiClick ={onNotiClick()}
            )
        },
        floatingActionButton = { HomeFAB(onFabClick = { onFabClick() }) }
    ) {
        it.calculateBottomPadding()
        val topPadding = it.calculateTopPadding()
        LazyColumn(
            modifier = Modifier.padding(top = topPadding)
        ) {
            chatMessages?.chat?.let { homeChatModel ->
                items(homeChatModel.size) { index ->
                    val chat = chatMessages?.chat!![index]
                    val isMessageToRead =
                        (chat.userId != chat.lastMessage?.sender?._id && chat.lastMessage?.status != "seen" && !chat.isGroup)
                    HomeChatCard(
                        isMessageToRead = isMessageToRead,
                        name = chat.name,
                        message = chat.lastMessage,
                        onClick = { navigateToChatScreen(chat) },
                        image = chat.avatar
                    )
                }
            }
        }
    }
}
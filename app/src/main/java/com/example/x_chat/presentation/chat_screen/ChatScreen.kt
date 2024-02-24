package com.example.x_chat.presentation.chat_screen

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.receive.HomeChatModel
import com.example.x_chat.presentation.chat_screen.components.BottomBar
import com.example.x_chat.presentation.chat_screen.components.ChatCard
import com.example.x_chat.presentation.chat_screen.components.ChatTopBar
import com.example.x_chat.presentation.chat_screen.components.ReplayBottomBar
import com.example.x_chat.presentation.g_chat_info_screen.GChatInfoScreen
import com.example.x_chat.presentation.image_upload_screen.ImageUploadScreen
import com.example.x_chat.presentation.p_chat_info_screen.PChatInfoScreen
import com.example.x_chat.presentation.util.DropDownItem
import kotlinx.coroutines.delay


data class ChatScreen(val chat: HomeChatModel) : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ChatScreenViewModel = hiltViewModel()
        ChatScreenImp(
            viewModel = viewModel,
            chat = chat,
            onBackClick = { navigator.pop() },
            onNavigateToImageUpload = { navigator.push(ImageUploadScreen(chatId = chat.chatId)) },
            pushToDetailedScreen = {
                if (chat.isGroup) {
                    navigator.push(GChatInfoScreen(userId = chat.userId, chatId = chat.chatId))
                } else {
                    navigator.push(PChatInfoScreen(userId = chat.userId, chatId = chat.chatId))
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreenImp(
    viewModel: ChatScreenViewModel,
    chat: HomeChatModel,
    onBackClick: () -> Unit,
    onNavigateToImageUpload: () -> Unit,
    pushToDetailedScreen: () -> Unit
) {
    val text = remember { mutableStateOf("") }
    val chatMessage by viewModel.chatMessage.observeAsState(initial = emptyList())
    val updates by viewModel.update.collectAsState()
    val listState = rememberLazyListState()
    val scrollToIndex = remember { mutableStateOf<Int?>(null) }
    val replayMessageId = remember { mutableStateOf<String?>(null) }
    val messageContent = remember { mutableStateOf<String?>(null) }
    val dropdownItems = listOf(
        DropDownItem(text = "Replay", Icons.AutoMirrored.Filled.Reply),
        DropDownItem(text = "Save", icon = Icons.Default.Save),
        DropDownItem(text = "Unsent", Icons.Default.Delete),
    )

    val isGroup = chat.isGroup
    val chatId = chat.chatId
    val userId = chat.userId

    LaunchedEffect(chatMessage) {
        if (chatMessage?.isEmpty() == true) {
            viewModel.onEvent(ChatEvent.UpdateChatId(chatId))
            viewModel.onEvent(ChatEvent.UpdateUserId(userId))
            viewModel.onEvent(ChatEvent.Group(isGroup))
            viewModel.receiveUserChat()
            delay(500)
        }
        if (chatMessage?.isNotEmpty() == true) {
            listState.scrollToItem((chatMessage?.size ?: -1))
        }
    }

    val context = LocalContext.current
    LaunchedEffect(updates, replayMessageId) {
        if (updates != "") {
            Toast.makeText(context, updates, Toast.LENGTH_LONG).show()
            viewModel.clearUpdates()
        }
    }
    LaunchedEffect(scrollToIndex.value) {
        if (scrollToIndex.value != null) {
            val index = scrollToIndex.value
            if (index != -1) {
                if (index != null) {
                    listState.scrollToItem(index)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            ChatTopBar(name = chat.name, onBackClick = { onBackClick() }, onAccountClick = {
                pushToDetailedScreen()
            }, image = chat.avatar)
        },
        bottomBar = {
            if (replayMessageId.value != null) {
                messageContent.value?.let {
                    ReplayBottomBar(
                        modifier = Modifier.height(80.dp),
                        textState = text,
                        messageModel = it,
                        onClick = {
                            sendMessage(
                                text = text,
                                viewModel = viewModel,
                                replayId = replayMessageId.value
                            )
                            replayMessageId.value = null
                            messageContent.value = null
                        }
                    )
                }

            } else {
                BottomBar(
                    textState = text,
                    onClick = { sendMessage(text = text, viewModel = viewModel) },
                    onNavigateToImageUpload = { onNavigateToImageUpload(); viewModel.clearChatMessages() }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        LazyColumn(
            modifier = Modifier
                .padding(bottom = bottomPadding, top = 10.dp),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Every Unsaved Messages will be deleted in 24hrs",
                        fontSize = 15.sp,
                    )
                }
            }
            chatMessage?.let { it1 ->
                items(it1.size) { index ->
                    val message = chatMessage!![index]
                    ChatCard(
                        isGroup = chat.isGroup,
                        message = message,
                        userId = userId,
                        onItemClick = { item ->
                            dropDownFun(
                                chatId = chatId,
                                userId = userId,
                                viewModel = viewModel,
                                it = item,
                                context = context,
                                replayId = replayMessageId,
                                mes = message,
                                replayContent = messageContent
                            )
                        },
                        dropdownItems = dropdownItems,
                        onReplayClick = { messageId ->
                            scrollToIndex.value = chatMessage?.indexOfFirst { messageModel ->
                                messageModel._id == messageId
                            }
                        }
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun dropDownFun(
    chatId: String,
    it: DropDownItem,
    userId: String,
    mes: MessageModel,
    context: Context,
    viewModel: ChatScreenViewModel,
    replayId: MutableState<String?>,
    replayContent: MutableState<String?>
) {
    viewModel.onEvent(ChatEvent.UpdateChatId(chatId))
    viewModel.onEvent(ChatEvent.UpdateMessageId(mes._id))
    viewModel.onEvent(ChatEvent.UpdateUserId(userId))

    if (it.text == "Replay" && !mes.isFile) {
        replayId.value = mes._id
        replayContent.value = mes.content

    } else {
        if (it.text == "Save") {
            viewModel.onEvent(ChatEvent.SaveMessage)
        }
        if (it.text == "Unsent") {
            if (mes.sender._id == userId) {
                viewModel.onEvent(ChatEvent.SendUnsentMessage)
            } else {
                Toast.makeText(context, "You can't do it", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun sendMessage(
    text: MutableState<String>,
    viewModel: ChatScreenViewModel,
    replayId: String? = ""
) {
    if (text.value != "") {
        Log.d("TokensCheck", text.value)
        val currentText = text.value
        viewModel.onEvent(ChatEvent.UpdateMessage(currentText))
        if (replayId != "")
            replayId?.let { ChatEvent.UpdateReplayId(it) }?.let { viewModel.onEvent(it) }
        viewModel.onEvent(ChatEvent.SendChat)
        text.value = ""
    }
}

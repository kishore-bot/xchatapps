package com.example.x_chat.presentation.g_chat_info_screen


import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.presentation.account_screen.components.AccountImage
import com.example.x_chat.presentation.add_member_screen.AddMembersScreen
import com.example.x_chat.presentation.g_chat_info_screen.components.AddMembersSection
import com.example.x_chat.presentation.g_chat_info_screen.components.EditField
import com.example.x_chat.presentation.g_chat_info_screen.components.GChatTopBar
import com.example.x_chat.presentation.g_chat_info_screen.components.GroupMembers
import com.example.x_chat.presentation.g_chat_info_screen.components.LeaveSection
import com.example.x_chat.presentation.home_screen.HomeScreen
import com.example.x_chat.presentation.image_upload_screen.ImageUploadScreen
import com.example.x_chat.presentation.image_upload_screen.components.WhichFie
import com.example.x_chat.presentation.util.showToastMessage

class GChatInfoScreen(
    val chatId: String,
    val userId: String
) : Screen {
    @Composable
    override fun Content() {
        val chatInfo = ChatInfo(
            userId = "",
            chatId = chatId
        )
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: GChatInfoViewModel = hiltViewModel()
        GChatInfoScreenImp(
            viewModel = viewModel,
            chatInfo = chatInfo,
            userId = userId,
            onBackClick = {
                navigator.pop()
            }, onImageClick = {
                val groupId = it
                val grp = WhichFie.GROUP
                navigator.push(ImageUploadScreen(chatId = groupId, which = grp))
            }, popToChat = {
                navigator.replaceAll(HomeScreen)
            }, navigateToAddMemScreen = {
                navigator.push(AddMembersScreen(chatId = chatId))
            })
    }
}

@Composable
fun GChatInfoScreenImp(
    viewModel: GChatInfoViewModel,
    chatInfo: ChatInfo,
    userId: String,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    popToChat: () -> Unit,
    navigateToAddMemScreen: () -> Unit

) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current as Activity
    val groupState by viewModel.groupModel.collectAsState(initial = null)
    val result by viewModel.result.collectAsState(initial = null)

    LaunchedEffect(groupState) {
        if (groupState == null) {
            viewModel.onEvent(GChatInfoEvent.UpdateChatInfo(chatInfo))
            viewModel.onEvent(GChatInfoEvent.FetchDetails)
            return@LaunchedEffect
        }
    }
    LaunchedEffect(result) {
        if (result?.message == "Success") {
            showToastMessage(result!!.message, context)
            popToChat()
        }
    }

    val isAboutEdit = remember {
        mutableStateOf(false)
    }
    val isNameEdit = remember {
        mutableStateOf(false)
    }
    val nameField = remember {
        mutableStateOf("")
    }
    val aboutField = remember {
        mutableStateOf("")
    }
    val about =
        if (groupState?.group?.groupDescription == "" || groupState?.group?.groupDescription == null) "Empty" else groupState?.group?.groupDescription
    val imageUrl =
        if (groupState?.group?.avatar == "") "" else groupState?.group?.avatar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollState)
    ) {
        GChatTopBar {
            onBackClick()
        }
        Spacer(modifier = Modifier.height(10.dp))
        AccountImage(
            onImageClick = {
                groupState?.group?.let { onImageClick(it._id) }
                viewModel.onEvent(GChatInfoEvent.Clear)
            },
            isIcon = true,
            imageUrl = imageUrl
        )
        Spacer(modifier = Modifier.height(10.dp))
        groupState?.group?.let {
            EditField(
                textState = nameField,
                label = it.groupName,
                contentLabel = "Name:  ",
                isEdit = isNameEdit.value
            ) {
                if (isNameEdit.value) {
                    uploadName(
                        name = nameField.value,
                        viewModel = viewModel,
                        groupId = groupState!!.group._id,
                        context = context
                    )
                    isNameEdit.value = false
                } else {
                    isNameEdit.value = true
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        EditField(
            textState = aboutField,
            label = about!!,
            contentLabel = "About:  ",
            isEdit = isAboutEdit.value
        ) {
            if (isAboutEdit.value) {
                uploadAbout(
                    about = aboutField.value,
                    viewModel = viewModel,
                    groupId = groupState!!.group._id,
                    context = context
                )
                isAboutEdit.value = false
            } else {
                isAboutEdit.value = true
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        groupState?.let {
            GroupMembers(
                members = it.owners,
                userId = userId,
                admin = groupState!!.group.admin,
                onRemoveClick = {
                    chatInfo.chatId?.let { it1 ->
                        removeUser(
                            chatId = it1,
                            userId = it,
                            viewModel = viewModel
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (groupState != null && groupState!!.group.admin == userId) {
            AddMembersSection {
                viewModel.onEvent(GChatInfoEvent.Clear)
                navigateToAddMemScreen()
             }
        }
        LeaveSection {
            chatInfo.chatId?.let { leaveGroup(chatId = it, viewModel = viewModel) }
        }

    }
}

private fun uploadName(
    name: String,
    groupId: String,
    viewModel: GChatInfoViewModel,
    context: Activity
) {
    if (name == "") {
        showToastMessage("Name Should Provide", context)
    } else {
        viewModel.onEvent(GChatInfoEvent.UpdateGroupId(groupId))
        viewModel.onEvent(GChatInfoEvent.UpdateName(name))
        viewModel.onEvent(GChatInfoEvent.UploadName)
    }
}

private fun uploadAbout(
    about: String,
    groupId: String,
    viewModel: GChatInfoViewModel,
    context: Activity
) {
    if (about == "") {
        showToastMessage("About Should Provide", context)
    } else {
        viewModel.onEvent(GChatInfoEvent.UpdateGroupId(groupId))
        viewModel.onEvent(GChatInfoEvent.UpdateAbout(about))
        viewModel.onEvent(GChatInfoEvent.UploadAbout)
    }
}

private fun leaveGroup(
    chatId: String,
    viewModel: GChatInfoViewModel,
) {
    val chatInfo = ChatInfo(
        chatId = chatId
    )
    viewModel.onEvent(GChatInfoEvent.UpdateChatInfo(chatInfo))
    viewModel.onEvent(GChatInfoEvent.LeaveGroup)

}

private fun removeUser(
    chatId: String,
    userId: String,
    viewModel: GChatInfoViewModel,
) {
    val chatInfo = ChatInfo(
        chatId = chatId,
        userId = userId
    )
    viewModel.onEvent(GChatInfoEvent.UpdateChatInfo(chatInfo))
    viewModel.onEvent(GChatInfoEvent.RemoveUser)

}
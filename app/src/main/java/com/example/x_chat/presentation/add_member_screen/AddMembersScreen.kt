package com.example.x_chat.presentation.add_member_screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.model.api.rec.sub.SearchUserModel
import com.example.x_chat.domain.model.api.send.CreateGroupModel
import com.example.x_chat.domain.model.api.send.sub.UserIdModel
import com.example.x_chat.presentation.create_group_screen.components.CreateGroupCard
import com.example.x_chat.presentation.create_group_screen.components.CreateGroupTopBar
import com.example.x_chat.presentation.util.showToastMessage

data class AddMembersScreen(
    val chatId: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: AddMembersViewModel = hiltViewModel()
        viewModel.oneEvent(AddMembersEvent.FetchFriends)
        val friends by viewModel.users.collectAsState(null)
        if(friends!=null){
            AddMembersScreenImp(
                viewModel = viewModel,
                onBackClick = {
                    navigator.pop()
                },
                friends = friends,
                chatId = chatId
            )
        }else{
            Text(text = "No Friends Found")
        }
    }
}

@Composable
fun AddMembersScreenImp(
    viewModel: AddMembersViewModel,
    chatId: String,
    friends: SearchModel? = null,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current as Activity
    val response by viewModel.response.collectAsState(null)


    LaunchedEffect(response) {
        if (response != null) {
            showToastMessage(response!!.message, context)
            onBackClick()
        }
    }
    Log.d("Friends",friends.toString())

    val friendList = remember {
        mutableStateListOf<SearchUserModel>().apply {
            friends?.users?.let { addAll(it) }
        }
    }
    val addedFriends = remember {
        mutableStateListOf<SearchUserModel>()
    }
    val userId = remember {
        mutableStateListOf<UserIdModel>()
    }
    Scaffold(
        topBar = {
            CreateGroupTopBar(
                onBackClick = onBackClick,
                topBar = "Create Chat",
                onCreateClick = {
                    if (addedFriends.size == 0) {
                        showToastMessage("Must Select AtLeast One User", context)
                    } else {
                        val group = CreateGroupModel(
                            groupName = chatId,
                            userId = userId
                        )
                        viewModel.oneEvent(AddMembersEvent.UpdateGroup(group))
                        viewModel.oneEvent(AddMembersEvent.AddMembers)
                    }
                },
            )
        }
    ) {
        val top = it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = top)
        ) {
            Text(
                text = "Select Users",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                items(friendList.size) { index ->
                    CreateGroupCard(user = friendList[index], onAddClick = { user ->
                        addedFriends.add(user)
                        val userIdModel = UserIdModel(user._id)
                        userId.add(userIdModel)
                        friendList.remove(user)
                    })
                }
            }
            Text(
                text = "Selected Users",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                items(addedFriends.size) { index ->
                    CreateGroupCard(user = addedFriends[index], onAddClick = { user ->
                        friendList.add(user)
                        val userIdModel = UserIdModel(user._id)
                        userId.remove(userIdModel)
                        addedFriends.remove(user)
                    }, isAdd = false)
                }
            }
        }
    }
}

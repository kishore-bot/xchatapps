package com.example.x_chat.presentation.create_chat_screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.R
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.presentation.create_chat_screen.components.CreateChatTopBar
import com.example.x_chat.presentation.create_group_screen.CreateGroupScreen
import com.example.x_chat.presentation.search_screen.components.SearchCard
import com.example.x_chat.presentation.util.showToastMessage

object CreateChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: CreateChatViewModel = hiltViewModel()
        CreateChatScreenImp(viewModel = viewModel,
            onBackClick = { navigator.pop() }, navigateToCGS = {
                navigator.push(CreateGroupScreen(it))
            })
    }
}

@Composable
fun CreateChatScreenImp(
    viewModel: CreateChatViewModel,
    onBackClick: () -> Unit,
    navigateToCGS: (SearchModel) -> Unit
) {

    val context = LocalContext.current as Activity
    val response by viewModel.response.collectAsState(null)
    val users by viewModel.users.collectAsState(null)

    LaunchedEffect(users) {
        if (users == null) {
            viewModel.oneEvent(CreateChatEvent.FetchFriends)
            return@LaunchedEffect
        }
    }
    LaunchedEffect(response) {
        if (response != null) {
            showToastMessage(response!!.message, context)
            viewModel.oneEvent(CreateChatEvent.Clear)
        }
    }


    Scaffold(
        topBar = {
            CreateChatTopBar(onBackClick)
        }
    ) {
        val pad = it.calculateTopPadding()
        if (users != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = pad)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .fillMaxWidth()
                            .background(color = colorResource(id = R.color.SendCard))
                            .clickable { navigateToCGS(users!!) }
                    )
                    {
                        Text(
                            text = "Create A Group",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
                users?.users?.let { it1 ->
                    items(it1.size) { index ->
                        SearchCard(user = users!!.users[index], onAddClick = { userId ->
                            viewModel.oneEvent(CreateChatEvent.UpdateUserId(userId))
                            viewModel.oneEvent(CreateChatEvent.CreatePChat)
                        })
                    }
                }
            }
        }
    }
}
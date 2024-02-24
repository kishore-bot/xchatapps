package com.example.x_chat.presentation.in_app_notification_screen

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.presentation.in_app_notification_screen.components.NotiTopBar
import com.example.x_chat.presentation.in_app_notification_screen.components.ReqCard
import com.example.x_chat.presentation.util.showToastMessage


object InAppNotificationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: InAppNotificationViewModel = hiltViewModel()
        InAppNotificationScreenImp(viewModel = viewModel,
            onBackClick = { navigator.pop() })
    }
}

@Composable
fun InAppNotificationScreenImp(
    viewModel: InAppNotificationViewModel,
    onBackClick: () -> Unit,
) {

    val context = LocalContext.current as Activity
    val response by viewModel.response.collectAsState(null)
    val req by viewModel.notifications.collectAsState(null)

    LaunchedEffect(req) {
        if (req == null) {
            viewModel.oneEvent(InAppNotificationEvent.Fetch)
        }
    }
    LaunchedEffect(response) {
        if (response != null) {
            showToastMessage(response!!.message, context)
            viewModel.oneEvent(InAppNotificationEvent.Clear)
        }
    }


    Scaffold(
        topBar = {
            NotiTopBar(onBackClick)
        }
    ) {
        val pad = it.calculateTopPadding()
        if (req != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = pad)
            ) {
                items(req!!.requests.size) { index ->
                    val reqModel = req!!.requests[index]
                    ReqCard(reqModel = reqModel, onAcceptClick = { id ->
                        viewModel.oneEvent(InAppNotificationEvent.UpdateUserId(id))
                        viewModel.oneEvent(InAppNotificationEvent.Accept)
                    })
                }
            }
        } else {
            Text(
                "No  Notification Found", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = pad),
                fontSize = 25.sp
            )
        }
    }
}
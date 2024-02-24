package com.example.x_chat.presentation.search_screen


import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.x_chat.presentation.search_screen.components.SearchCard
import com.example.x_chat.presentation.search_screen.components.SearchTopBar
import com.example.x_chat.presentation.util.showToastMessage

object SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreenImp(viewModel = viewModel, onBackClick = { navigator.pop() })
    }
}

@Composable
fun SearchScreenImp(
    viewModel: SearchViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current as Activity
    val response by viewModel.response.collectAsState(null)
    val users by viewModel.users.collectAsState(null)

    LaunchedEffect(response) {
        if (response != null) {
            showToastMessage(response!!.message, context)
            viewModel.clear()
        }
    }
    Scaffold(
        topBar = {
            SearchTopBar(onSearch = {
                viewModel.oneEvent(SearchEvent.UpdateSearch(it))
                viewModel.oneEvent(SearchEvent.SearchUser)
            }, onBackClick = { onBackClick() })
        }
    ) {
        val pad = it.calculateTopPadding()
        if (users != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = pad)
            ) {
                users?.users?.let { it1 ->
                    items(it1.size) { index ->
                        SearchCard(user = users!!.users[index], onAddClick = {userId->
                            viewModel.oneEvent(SearchEvent.UpdateUserId(userId))
                            viewModel.oneEvent(SearchEvent.SendRequest)
                        })
                    }
                }
            }
        }
    }
}
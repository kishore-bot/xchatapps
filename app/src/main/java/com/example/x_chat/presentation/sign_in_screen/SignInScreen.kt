package com.example.x_chat.presentation.sign_in_screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.x_chat.domain.model.api.rec.UserCredentials
import com.example.x_chat.presentation.home_screen.HomeScreen
import com.example.x_chat.presentation.sign_up_screen.SignUpScreen
import com.example.x_chat.presentation.util.ChatButton
import com.example.x_chat.presentation.util.ChatTextField
import com.example.x_chat.presentation.util.PassWordTextFiled
import com.example.x_chat.presentation.util.showToastMessage

object SignInScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SignInViewModel = hiltViewModel()
        SignInScreenImp(
            viewModel,
            onSignUp = {
                navigator.push(SignUpScreen)
            },
            onSuccess = {
                navigator.push(HomeScreen)
            }
        )
    }
}

@Composable
fun SignInScreenImp(
    viewModel: SignInViewModel,
    onSuccess: () -> Unit,
    onSignUp: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    val email = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val result by viewModel.result.observeAsState(false)

    LaunchedEffect(result) {
        if (result) {
            showToastMessage(message = "Success", activity = activity)
            viewModel.onEvent(SignInEvent.OnSuccess)
            onSuccess()
        }
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In To Account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.Text)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier.height(10.dp))
        ChatTextField(textState = email, text = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        PassWordTextFiled(passwordState = passwordState)
        Spacer(modifier = Modifier.height(20.dp))
        ChatButton(title = "SignIn") {
            signInUser(
                email = email.value,
                password = passwordState.value,
                activity = activity,
                viewModel = viewModel
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Are new to App? ",
                fontSize = 15.sp,
                color = colorResource(id = R.color.Text)
            )
            TextButton(onClick = {
                onSignUp()
            }) {
                Text(
                    text = "SignUp ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.Text)
                )

            }
        }
    }
}


private fun signInUser(
    email: String,
    password: String,
    activity: Activity,
    viewModel: SignInViewModel,

    ) {
    if (email == "" || password == "") {
        showToastMessage(message = "Select All Field", activity = activity)
    } else {
        val userCredentials = UserCredentials(
            username = "",
            email = email,
            password = password,
            phoneNo = 0
        )
        viewModel.onEvent(SignInEvent.UpdateUser(userCredentials = userCredentials))
        viewModel.onEvent(SignInEvent.SignInUser)
    }
}
package com.example.x_chat.presentation.sign_up_screen

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
import com.example.x_chat.presentation.sign_in_screen.SignInScreen
import com.example.x_chat.presentation.util.ChatButton
import com.example.x_chat.presentation.util.ChatTextField
import com.example.x_chat.presentation.util.PassWordTextFiled
import com.example.x_chat.presentation.util.showToastMessage
import kotlinx.coroutines.delay

object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SignUpViewModel = hiltViewModel()
        SignUpScreenImp(
            viewModel,
            onLogin = {
                navigator.push(SignInScreen)
            },
            onSuccess = { navigator.push(HomeScreen) }

        )
    }

}

@Composable
fun SignUpScreenImp(
    viewModel: SignUpViewModel,
    onSuccess: () -> Unit,
    onLogin: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phNo = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val result by viewModel.result.observeAsState(false)

    LaunchedEffect(result) {
        if (result) {
            showToastMessage(message = "Success", activity = activity)
            delay(200)
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
            text = "Create A New Account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.Text)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ChatTextField(textState = name, text = "Enter Your Name")
        Spacer(modifier = Modifier.height(10.dp))
        ChatTextField(textState = email, text = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        ChatTextField(textState = phNo, text = "Mobile No")
        Spacer(modifier = Modifier.height(10.dp))
        PassWordTextFiled(passwordState = passwordState)
        Spacer(modifier = Modifier.height(20.dp))
        ChatButton(title = "SignIn") {
            signIn(
                name = name.value,
                email = email.value,
                password = passwordState.value,
                activity = activity,
                viewModel = viewModel,
                phNo = phNo.value
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Are you already present? ",
                fontSize = 15.sp,
                color = colorResource(id = R.color.Text)
            )
            TextButton(onClick = {
                onLogin()
            }) {
                Text(
                    text = "LogIn ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.Text)
                )

            }
        }
    }
}


private fun signIn(
    name: String,
    email: String,
    phNo: String,
    password: String,
    activity: Activity,
    viewModel: SignUpViewModel,

    ) {
    if (email == "") {
        showToastMessage(message = "Email Should Not Be Null", activity = activity)
    } else if (password == "") {
        showToastMessage(message = "Password Should Not be null", activity = activity)
    } else if (name == "") {
        showToastMessage(message = "Name Should Not be null", activity = activity)
    } else if (phNo.length < 10 || phNo.length > 13) {
        showToastMessage(message = "Phone No Should be proper", activity = activity)
    } else {
        val user = UserCredentials(
            username = name,
            email = email,
            password = password,
            phoneNo = phNo.toBigDecimal()
        )
        viewModel.onEvent(SignUpEvent.UpdateUser(userCredentials = user))
        viewModel.onEvent(SignUpEvent.SignUpUser)
    }
}
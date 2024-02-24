package com.example.x_chat.presentation.account_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.R
import com.example.x_chat.domain.model.api.rec.UserModel

@Composable
fun AccountContentCard(
    user: UserModel,
    isEdit: Boolean = false,
    textState: MutableState<String>,
    isLogOut: () -> Unit,
    label: String,
    isAboutUpdate: () -> Unit
) {

    Column {
        Content(label = "UserName : ", value = user.username)
        Content(label = "Email : ", value = user.email)
        Content(label = "Ph No :", value = user.phoneNo.toString())
        Content(label = "Created At : ", value = user.createAt.substring(0, 10))
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "About : ", fontSize = 20.sp, fontWeight = FontWeight.W600)
                TextField(
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp),
                    value = textState.value,
                    textStyle = TextStyle(fontSize = 20.sp),
                    placeholder = {
                        Text(
                            label,
                            color = colorResource(id = R.color.Text),
                            fontSize = 20.sp
                        )
                    },
                    onValueChange = { newText -> textState.value = newText },
                    enabled = isEdit,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )

                )

                IconButton(onClick = { isAboutUpdate() }) {
                    if (isEdit)
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Rounded.Save,
                            contentDescription = null
                        )
                    else
                        Icon(
                            modifier = Modifier.width(30.dp),
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null
                        )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.LightGray),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Log Out ", fontSize = 20.sp, fontWeight = FontWeight.W600)
                IconButton(onClick = { isLogOut() }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        tint = Color.Red,
                        imageVector = Icons.AutoMirrored.Rounded.Logout,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

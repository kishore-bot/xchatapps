package com.example.x_chat.presentation.in_app_notification_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.R
import com.example.x_chat.domain.model.api.rec.sub.RequestsModel
import com.example.x_chat.presentation.util.ChatButton
import com.example.x_chat.presentation.util.CircleImg

@Composable
fun ReqCard(
    reqModel: RequestsModel,
    onAcceptClick: (String) -> Unit
) {
    val user = reqModel.senderId
    val status = reqModel.isAccepted
    val createdAt = reqModel.createdAt

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.SendCard))


    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(20.dp)),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    CircleImg(image = user.avatar, modifier = Modifier.size(60.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(user.username, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        Text(createdAt.substring(0,9), fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    }
                }
                if (!status ) {
                    ChatButton(title = "Accept") {
                        onAcceptClick(reqModel._id)
                    }
                } else {
                    Text(
                        text = "Accepted",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    )
                }
            }
        }
    }
}

package com.example.x_chat.presentation.search_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.R
import com.example.x_chat.domain.model.api.rec.sub.SearchUserModel
import com.example.x_chat.presentation.util.CircleImg

@Composable
fun SearchCard(
    user: SearchUserModel,
    modifier: Modifier = Modifier,
    onAddClick: (String) -> Unit
) {
    Box(
        modifier = modifier
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
                    Text(user.username, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                Card(
                    shape = CircleShape, modifier = Modifier.size(40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),
                ) {
                    IconButton(onClick = { onAddClick(user._id) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            tint = Color.Red,
                            modifier = Modifier.size(25.dp)
                        )

                    }
                }
            }
        }
    }
}
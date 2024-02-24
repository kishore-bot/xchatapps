package com.example.x_chat.presentation.chat_screen.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.x_chat.R
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.presentation.util.DropDownItem
import com.example.x_chat.presentation.util.DropdownMenuContent

@Composable
fun ChatCard(
    userId: String,
    message: MessageModel,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit,
    dropdownItems: List<DropDownItem>,
    isGroup: Boolean = false,
    onReplayClick: (String) ->
    Unit,
) {

    val color =
        if (userId == message.sender._id) colorResource(id = R.color.SendCard) else colorResource(id = R.color.ReceiveCard)

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset(0.dp, 0.dp))
    }

    var itemHeight by remember {
        mutableStateOf(0.dp)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val density = LocalDensity.current

    Column {
        Box(
            modifier = modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
        ) {
            if (!message.isFile) {
                ChatCardContent(
                    userId = userId,
                    message = message,
                    onReplayClick = { onReplayClick(it) })
            } else {
                ImageCard(
                    messageModel = message,
                    userId = userId,
                )
            }
        }
        DropdownMenuContent(
            isContextMenuVisible = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            pressOffset = pressOffset,
            itemHeight = itemHeight,
            onItemClick = {
                onItemClick(it)
                isContextMenuVisible = false
            },
            dropdownItems = dropdownItems
        )
    }
    SeenIcon(message = message, userId = userId, isGroup = isGroup)
}
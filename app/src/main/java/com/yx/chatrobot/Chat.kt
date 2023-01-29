package com.yx.chatrobot

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yx.chatrobot.data.Message
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(viewModel: MainViewModel) {

    val listState = viewModel.listState
    Surface() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                Message(
                    viewModel,
                    modifier = Modifier.height(510.dp),
                    listState = listState
                )
                UserInput(viewModel, listState)
            }
        }
    }
}

@Composable
fun Message(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    listState: LazyListState
) {
    val chatList = remember {
        viewModel.messages
    }
    Surface(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            state = listState
        ) {
            items(chatList) { item ->
                MessageItem(message = item)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserInput(
    viewModel: MainViewModel,
    listState: LazyListState
) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
//            .height(64.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Surface {
            Box(
                modifier = Modifier
//                    .height(64.dp)
                    .weight(1f)
                    .align(Alignment.Bottom)
            ) {
                val fullWidthModifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                val textFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                OutlinedTextField(
                    modifier = fullWidthModifier,
                    value = textFieldValue.value,
                    label = {
                        if (textFieldValue.value.text.isEmpty()) {
                            Text("输入您想问的")
                        } else {
                            Text("点击回车等待回复")
                        }
                    },
                    placeholder = { Text("请输入内容") },
                    onValueChange = { newValue ->
                        textFieldValue.value = newValue
                    },
                    isError = textFieldValue.value.text.isEmpty(),
                    trailingIcon = {
                        if (textFieldValue.value.text.isEmpty()) {
                            IconButton(onClick = {
                                // 开始编辑
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = {
                                if (!textFieldValue.value.text.isEmpty()) {
                                    viewModel.getAiReply(textFieldValue.value.text)
                                    viewModel.messages.add(
                                        Message(
                                            R.drawable.user_avatar,
                                            "自己",
                                            Date().time / 1000,
                                            textFieldValue.value.text,
                                            true
                                        )
                                    )
                                    textFieldValue.value = TextFieldValue("")
                                    keyboard?.hide()
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(viewModel.messages.size-1)
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "输入内容不能为空!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }) {
                                Icon(Icons.Default.Send, contentDescription = null)
                            }
                        }
                    },

                    )
            }
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        Image(
            painter = painterResource(message.imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))


        Column {
            Text(
                text = message.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 3.dp,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(all = 4.dp),
                    fontSize = 20.sp
                )
            }
        }
    }


}


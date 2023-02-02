package com.yx.chatrobot.ui.config

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.data.ConfigUiState
import com.yx.chatrobot.data.bottomDrawerList
import com.yx.chatrobot.data.modelList
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.component.ChatHeader

@ExperimentalMaterialApi
@Composable
fun DrawerContent(
    bottomAppBarPadding: Dp,
    openDrawer: () -> Unit,
    configUiState: ConfigUiState,
    isListShow: Boolean,
    configViewModel: ConfigViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Column {
        var switched by remember { mutableStateOf(false) }
        val onSwitchedChange: (Boolean) -> Unit = { switched = it }
        ChatHeader(text = "主题配置")
        ListItem(
            text = { Text(text = "深色模式") },
            trailing = {
                Switch(
                    checked = switched,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
            },
            modifier = Modifier.toggleable(
                value = switched,
                onValueChange = onSwitchedChange
            )
        )
        ChatHeader(text = "接口配置")
        ListItem(
            text = { Text(text = "model") },
            secondaryText = { Text(text = "用于模型的选择") },
            trailing = {
                Button(
                    onClick = {
                        configViewModel.updateState(true, "model")
                        openDrawer()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {
                        Text(text = configUiState.model)
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = null
                        )
                    }
                }
            },
        )
        ListItem(
            text = { Text(text = "max_tokens") },
            secondaryText = { Text(text = "The maximum number of tokens to generate in the completion") },
            trailing = {
                Button(
                    onClick = {
                        configViewModel.updateState(false, "maxTokens")
                        openDrawer()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {

                        Text(text = configUiState.maxTokens.toString())
                        Icon(
                            imageVector = Icons.Default.Edit,
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = null
                        )
                    }
                }
            }
        )
        ListItem(
            text = { Text(text = "temperature") },
            secondaryText = { Text(text = "0-1 之间的值，值越大，可信度越低") },
            trailing = {
                Button(
                    onClick = {
                        configViewModel.updateState(false, "temperature")
                        openDrawer()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = null
                        )
                        Text(text = configUiState.temperature.toString())
                    }
                }
            }
        )
        ListItem(
            text = { Text(text = "top_p") },
            secondaryText = { Text(text = "类似于temperature") },
            trailing = {
                Button(
                    onClick = {
                        configViewModel.updateState(false, "topP")
                        openDrawer()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = null
                        )
                        Text(text = configUiState.topP.toString())
                    }
                }
            }
        )
        ListItem(
            text = { Text(text = "stop") },
            secondaryText = { Text(text = "Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop sequence.") },
            trailing = {
                Button(
                    onClick = {
                        configViewModel.updateState(false, "stop")
                        openDrawer()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = null
                        )
                        Text(text = configUiState.stop)
                    }
                }
            }
        )
    }
}
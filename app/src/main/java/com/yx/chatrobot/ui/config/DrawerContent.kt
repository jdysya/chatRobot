package com.yx.chatrobot.ui.config

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.yx.chatrobot.data.modelList
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.component.ChatHeader
import com.yx.chatrobot.ui.component.ConfigListItem

@ExperimentalMaterialApi
@Composable
fun DrawerContent(
    bottomAppBarPadding: Dp,
    openDrawer: () -> Unit,
    configUiState: ConfigUiState,
    isListShow: Boolean,
    configViewModel: ConfigViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ChatHeader(text = "主题配置")
            ThemeConfig(
                openDrawer = openDrawer,
                configViewModel = configViewModel
            )
        }
        item {
            ChatHeader(text = "接口配置")
            InterfaceConfig(
                configUiState = configUiState,
                openDrawer = openDrawer,
                configViewModel = configViewModel
            )
        }
        item {
            ChatHeader(text = "其他配置")
            ExtraConfig(
                configUiState = configUiState,
                openDrawer = openDrawer,
                configViewModel = configViewModel
            )
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExtraConfig(
    configUiState: ConfigUiState,
    configViewModel: ConfigViewModel,
    openDrawer: () -> Unit,
) {
    ListItem(
        text = {
            Text(
                style = MaterialTheme.typography.body1, text = "机器人昵称"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "聊天界面中机器人显示的名称"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "robotName")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.robotName
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        modifier = Modifier.padding(end = 4.dp),
                        contentDescription = null
                    )
                }
            }
        },
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThemeConfig(
    openDrawer: () -> Unit,
    configViewModel: ConfigViewModel
) {
    val switched = configViewModel.themeState.collectAsState().value
    val fontSizeState = configViewModel.fontState.collectAsState().value
    val onSwitchedChange: (Boolean) -> Unit = {
//        switched = it
        Log.d("mytest", it.toString())
    }
    ListItem(
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "深色模式"
            )
        },
        trailing = {
            Switch(
                checked = switched,
                onCheckedChange = {
                    configViewModel.saveThemeState(!switched)
                }
            )
        }
    )
    ListItem(
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "字体大小"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "调整应用内字体大小"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(true, "fontSize")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        style = MaterialTheme.typography.body2,
                        text = fontSizeState
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        modifier = Modifier.padding(end = 4.dp),
                        contentDescription = null
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InterfaceConfig(
    configUiState: ConfigUiState,
    configViewModel: ConfigViewModel,
    openDrawer: () -> Unit,
) {
    ListItem(

        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "model"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "选择对应的 AI 模型"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(true, "model")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.model
                    )
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
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "max_tokens"
            )
        },

        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "决定 AI 回复的长度（不能超过 4000）"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "maxTokens")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {


                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.maxTokens.toString()
                    )
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
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "temperature"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "0-1 之间的值，值越大，可信度越低"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "temperature")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.temperature.toString()
                    )
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
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "top_p"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "类似于temperature"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "topP")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.topP.toString()
                    )
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
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "frequencyPenalty"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "-2.0到 2.0 之间的一位小数,正值会根据新符号在文本中的现有频率来惩罚它们，从而降低模型逐字重复同一行的可能性。"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "frequencyPenalty")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.frequency_penalty.toString()
                    )
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
        text = {
            Text(
                style = MaterialTheme.typography.body1,
                text = "presencePenalty"
            )
        },
        secondaryText = {
            Text(
                style = MaterialTheme.typography.body2,
                text = "-2.0到2.0之间的一位小数。正值会根据新标记到目前为止是否出现在文本中来惩罚它们，从而增加模型谈论新主题的可能性。"
            )
        },
        trailing = {
            Button(
                onClick = {
                    configViewModel.updateState(false, "presencePenalty")
                    openDrawer()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Row {

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = configUiState.presence_penalty.toString()
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        modifier = Modifier.padding(end = 4.dp),
                        contentDescription = null
                    )
                }
            }
        }
    )

}
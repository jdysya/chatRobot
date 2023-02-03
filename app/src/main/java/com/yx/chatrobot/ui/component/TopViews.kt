package com.yx.chatrobot

import android.content.Intent
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun TopBarView(
    navController: NavController,
    currentScreen: MutableState<Screen>,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val viewModel: MainViewModel = viewModel()
    TopAppBar(
        contentColor = Color.White,
        title = {
            Text(currentScreen.value.title, style = MaterialTheme.typography.h5)
        },
        navigationIcon = {
            IconButton(onClick = {
                //（1）打开侧滑菜单
                scope.launch {
                    scaffoldState.drawerState.open()
                }

            }) {
                Icon(currentScreen.value.icon, currentScreen.value.title)
            }
        },
        actions = {
            IconButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Icon(Icons.Filled.MoreVert, "More")
            }
            if (expanded.value)
                TopMenuView(expanded, navController = navController)
        })
}


/**
 * Top menu view
 * 定义右侧菜单
 */
@Composable
fun TopMenuView(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    val context = LocalContext.current
    DropdownMenu(expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }) {
        MenuItemView(
            icon = Icons.Filled.Share,
            title = "分享",
            action = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "分享")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "我正在使用聊天机器人，推荐你也使用:https://cloud.jdysya.top/#s/88r40pXg"
                    )
                }
                context.startActivity(
                    Intent.createChooser(
                        intent,
                        "聊天机器人"
                    )
                )
            }
        )
        MenuItemView(
            icon = Icons.Filled.ExitToApp,
            title = "退出登录",
            action = {
                navController.popBackStack()
            }

        )
    }
}

@Composable
fun MenuItemView(icon: ImageVector, title: String, action: () -> Unit) {
    val viewModel: MainViewModel = viewModel()
    DropdownMenuItem(onClick = action) {
        Icon(imageVector = icon, contentDescription = title)
        Text(
            style = MaterialTheme.typography.subtitle2,
            text = title
        )
    }
}
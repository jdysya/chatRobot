package com.yx.chatrobot

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.data.Message
import kotlinx.coroutines.launch


@Composable
fun TopBarView(currentScreen: MutableState<Screen>, scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    var expanded = remember { mutableStateOf(false) }
    val viewModel: MainViewModel = viewModel()
    TopAppBar(
        contentColor = Color.White,
        title = {
            Text(currentScreen.value.title, fontSize = viewModel.fontSize.sp)
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
                TopMenuView(expanded)
        })
}


@Composable
fun ModalDrawerTopAppBar(openDrawer: () -> Unit) {
    var expanded = remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text("ModalDrawer")
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Icon(Icons.Filled.MoreVert, "More")
            }
            if (expanded.value)
                TopMenuView(expanded)
        }
    )
}

/**
 * Top menu view
 * 定义右侧菜单
 */
@Composable
fun TopMenuView(expanded: MutableState<Boolean>) {
    DropdownMenu(expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }) {
        MenuItemView(icon = Icons.Filled.Share, title = "分享") {
            //（2）处理数据分享

        }
        MenuItemView(icon = Icons.Filled.ExitToApp, title = "退出") {
            //（3）退出应用

        }
    }
}

@Composable
fun MenuItemView(icon: ImageVector, title: String, action: () -> Unit) {
    val viewModel:MainViewModel = viewModel()
    DropdownMenuItem(onClick = {
        //（4）增加下拉菜单的动作处理

    }) {
        Icon(imageVector = icon, contentDescription = title)
        Text(title)
    }
}
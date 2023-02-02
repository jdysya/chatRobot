package com.yx.chatrobot.ui.config

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.DrawerButton
import com.yx.chatrobot.data.ConfigUiState
import com.yx.chatrobot.data.bottomDrawerList
import com.yx.chatrobot.data.configItem
import com.yx.chatrobot.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ConfigShow() {
    ConfigScreen()
}

@ExperimentalMaterialApi
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val isListShow by remember { configViewModel.isListShow } // 是否通过选项输入
    val configUiState = configViewModel.configUiState
    val coroutineScope = rememberCoroutineScope()
    val drawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val openDrawer: () -> Unit = { coroutineScope.launch { drawerState.expand() } }
    var selectedBottomDrawerIndex by remember { configViewModel.drawerIndex }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    configViewModel.saveConfig()
                    Log.d("mytest", configViewModel.configUiState.toString())
                },
                modifier = Modifier,
            ) {
                Icon(
                    Icons.Filled.Save,
                    contentDescription = null
                )
            }
        },
    ) {

        val bottomAppBarPadding = it.calculateBottomPadding()
        BottomDrawerComponent(
            bottomAppBarPadding,
            drawerState,
            openDrawer,
            selectedIndex = selectedBottomDrawerIndex,
            onSelected = { index: Int, value: String ->
                selectedBottomDrawerIndex = index
                configViewModel.updateValue(value)
            },
            isListShow = isListShow,
            configUiState = configUiState,
            configViewModel = configViewModel
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun BottomDrawerComponent(
    bottomAppBarPadding: Dp,
    drawerState: BottomDrawerState,
    openDrawer: () -> Unit,
    selectedIndex: Int, onSelected: (Int, String) -> Unit,
    isListShow: Boolean,
    configUiState: ConfigUiState,
    configViewModel: ConfigViewModel
) {
    BottomDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            if (!isListShow) {
                DrawerContentInput(configViewModel, configUiState)
            } else {
                DrawerContentSelect(
                    selectedIndex = selectedIndex,
                    onSelected = onSelected,
                    configViewModel = configViewModel
                )
            }
        },
        content = {
            DrawerContent(
                bottomAppBarPadding,
                openDrawer,
                configUiState = configUiState,
                isListShow = isListShow
            )
        }
    )
}


@Composable
fun DrawerContentSelect(
    selectedIndex: Int,
    onSelected: (Int, String) -> Unit,
    configViewModel: ConfigViewModel
) {
    val listName = remember {
        configViewModel.selectToShow
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 600.dp)
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text("Mail", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "user@abc.com")
            }
        }
        configItem.get(listName.value)?.forEachIndexed { index, pair ->
            val label = pair.first
            val imageVector = pair.second
            DrawerButton(
                icon = imageVector,
                label = label,
                isSelected = selectedIndex == index,
                action = {
                    onSelected(index, label)
                }
            )
        }
    }
}


@Composable
fun DrawerContentInput(
    configViewModel: ConfigViewModel,
    configUiState: ConfigUiState,
) {
    val fullWidthModifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    var inputValue = configViewModel.currentInputValue
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 600.dp)
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text("Mail", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "user@abc.com")

            }
        }
        OutlinedTextField(
            modifier = fullWidthModifier,
            value = configViewModel.currentInputValue,
            label = {
                Text(text = "配置")
            },
            placeholder = { Text("请输入对应的数值") },
            onValueChange = {
                configViewModel.updateValue(it)
                configViewModel.currentInputValue = it
            },
        )
    }
}






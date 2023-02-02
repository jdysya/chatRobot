package com.yx.chatrobot

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.config.ConfigScreen


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun HomeScreen() {
    ChatScreen()
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingScreen() {
    ConfigScreen()
}


@Composable
fun HelpScreen() {


}


sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val loadScreen: @Composable () -> Unit
) {
    @RequiresApi(Build.VERSION_CODES.O)
    object HomePage : Screen("home", "首页", Icons.Filled.Home, {
        HomeScreen()
    })

    object SettingPage : Screen("setting", "配置", Icons.Filled.Settings, {
        SettingScreen()
    })

    object HelpPage : Screen("help", "帮助", Icons.Filled.Info, {
        HelpScreen()
    })
}

val screens = listOf<Screen>(Screen.HomePage, Screen.SettingPage, Screen.HelpPage)
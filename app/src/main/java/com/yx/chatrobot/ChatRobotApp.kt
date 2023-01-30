package com.yx.chatrobot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatRobotApp(navController: NavHostController = rememberNavController()){
    ChatNavHost(navController = navController)
}
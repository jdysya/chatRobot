package com.yx.chatrobot

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.theme.ChatRobotTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatRobotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ChatRobotApp()
                }
            }
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState()
    var selectedIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { coroutineScope.launch { drawerState.open() } }
    val closeDrawer: () -> Unit = { coroutineScope.launch { drawerState.close() } }
    // 当前页面
    val currentScreen: MutableState<Screen> = remember { mutableStateOf(Screen.HomePage) }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            //(1)增加顶部动作栏
            TopBarView(
                navController = navController,
                currentScreen = currentScreen,
                scaffoldState = scaffoldState)
        },
        drawerContent = {
            //(2)增加侧滑菜单en, scaffoldState = scaffoldState)
            ModalDrawerContentHeader(viewModel)
            ModelDrawerContentBody(
                selectedIndex,
                onSelected = {
                    selectedIndex = it
                },
                currentScreen = currentScreen,
                scaffoldState = scaffoldState
            )

        },
        content = {
            //（3）增加加载当前页面的处理
            currentScreen.value.loadScreen()
        }
    )

}
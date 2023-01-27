package com.yx.chatrobot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yx.chatrobot.ui.theme.ChatRobotTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatRobotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
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
            TopBarView(currentScreen = currentScreen, scaffoldState = scaffoldState)
//            ModalDrawerTopAppBar(openDrawer)
        },
        drawerContent = {
            //(2)增加侧滑菜单
//            TopBarView(currentScreen = currentScreen, scaffoldState = scaffoldState)
//            DrawerMenuView(currentScreen = currentScreen, scaffoldState = scaffoldState)
            ModalDrawerContentHeader()
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
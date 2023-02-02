package com.yx.chatrobot

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.ui.AppViewModelProvider
import kotlinx.coroutines.launch


/**
 * Drawer menu view
 * 定义侧滑的下面的菜单
 * @param selectedScreen Screens 要选择切换的界面
 * @param onMenuSelected Function1<[@kotlin.ParameterName] Screens, Unit>? 要处理的切换动作
 */

@Composable
fun DrawerMenuView(
    currentScreen: MutableState<Screen>,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    // 选项按列排布
    Column(modifier = Modifier.fillMaxWidth()) {
        //对每个界面进行遍历访问
        screens.forEach { screen ->
            Row(
                content = {
                    // 定义IconButton
                    IconButton(onClick = {//点击事件
                        //（1）导航到已选的当前页面
                        currentScreen.value = screen

                        //（2）关闭侧滑菜单
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }) {
                        Row {
                            Icon(imageVector = screen.icon, contentDescription = screen.title)
                            Text(
                                screen.title,
                                fontSize = 24.sp,
                                // 颜色随当前所在页面进行改变
                                color = if (currentScreen.value.route == screen.route)
                                    colorResource(id = R.color.teal_700)
                                else colorResource(id = R.color.purple_200)
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )//end Row
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ModalDrawerContentHeader(viewModel: MainViewModel) {
    val userState = viewModel.userState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(20.dp)
    ) {

        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.user_avatar),
            contentDescription = null
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = userState.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = userState.description,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        }
    }
}


@Composable
fun ModelDrawerContentBody(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    currentScreen: MutableState<Screen>,
    scaffoldState: ScaffoldState,
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth()) {
        screens.forEach { screen ->
            DrawerButton(
                icon = screen.icon,
                label = screen.title,
                isSelected = currentScreen.value.route == screen.route,
                action = {
                    currentScreen.value = screen
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        }
    }
}


@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.8f
    }
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.9f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null, // decorative
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    fontWeight = FontWeight.Bold,
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

val modalDrawerList = listOf(
    Pair("首页", Icons.Filled.Home),
    Pair("配置", Icons.Filled.Settings),
    Pair("帮助", Icons.Filled.Help)
)



package com.yx.chatrobot.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yx.chatrobot.data.LoginUiState
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.component.ChatAlertDialog
import com.yx.chatrobot.ui.component.ChatRobotBar

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            ChatRobotBar("登录页面")
        },
    ) { innerPadding ->
        LoginBody(
            modifier = modifier.padding(innerPadding),
            loginViewModel = loginViewModel,
            navController = navController
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    var showAlertDialog by remember {
        mutableStateOf(false)
    }
    val uiState = loginViewModel.loginUiState
    val context = LocalContext.current
    var alertTitle by remember {
        mutableStateOf("这是标题")
    }
    var alertContent by remember {
        mutableStateOf("这是内容")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ItemInputForm(
            loginUiState = loginViewModel.loginUiState,
            onValueChange = loginViewModel::updateUiState,
            loginViewModel = loginViewModel
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = {
                    loginViewModel.login(
                        navigateToHome = {
                            navController.navigate("home/${it}")
                        },
                        errorAlert = { title: String, content: String ->
                            alertTitle = title
                            alertContent = content
                            showAlertDialog = !showAlertDialog

                        },
                        normalAlert = {
                            Toast.makeText(context, "登录成功，亲爱的$it", Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "登录",
                    style = MaterialTheme.typography.body2
                )
                if (showAlertDialog) {
                    ChatAlertDialog(
                        title = alertTitle,
                        content = alertContent,
                        onDismiss = { showAlertDialog = !showAlertDialog }
                    )
                }


            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp),
                onClick = {
                    loginViewModel.register(
                        infoAlert = { title: String, content: String ->
                            alertTitle = title
                            alertContent = content
                            showAlertDialog = !showAlertDialog
                        }
                    )
                }
            ) {
                Text(
                    text = "注册",
                    style = MaterialTheme.typography.body2
                )
                if (showAlertDialog) {
                    ChatAlertDialog(
                        title = alertTitle,
                        content = alertContent,
                        onDismiss = { showAlertDialog = !showAlertDialog }
                    )
                }
            }
        }

    }
}

@Composable
fun ItemInputForm(
    loginUiState: LoginUiState,
    onValueChange: (LoginUiState) -> Unit = {},
    loginViewModel: LoginViewModel
) {
    val fullWidthModifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    OutlinedTextField(
        modifier = fullWidthModifier,
        value = loginUiState.account,
        label = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "账号"
            )
        },
        placeholder = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "请输入账号"
            )
        },
        onValueChange = { onValueChange(loginUiState.copy(account = it)) },
    )
    OutlinedTextField(
        modifier = fullWidthModifier,
        value = loginUiState.password,
        label = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "密码"
            )
        },
        placeholder = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "请输入密码"
            )
        },
        onValueChange = { onValueChange(loginUiState.copy(password = it)) },
        // 设置为密码模式
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )

    OutlinedTextField(
        modifier = fullWidthModifier,
        value = loginUiState.passRepeat,
        label = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "验证密码"
            )
        },
        placeholder = {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = "注册请输入两次密码"
            )
        },
        onValueChange = { onValueChange(loginUiState.copy(passRepeat = it)) },
        // 设置为密码模式
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )

}

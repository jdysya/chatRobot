package com.yx.chatrobot.ui.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yx.chatrobot.data.LoginUiState
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.component.ChatRobotBar
import com.yx.chatrobot.ui.component.ChatRobotInput
import kotlin.math.log

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
    val uiState = loginViewModel.loginUiState
    val context = LocalContext.current

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
                        navigateToHome = { navController.navigate("home/${it}") },
                        errorAlert = { Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show() }
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("登录")
            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp),
                onClick = { loginViewModel.register() }
            ) {
                Text("注册")
            }
        }
        Text(text = loginViewModel.currentStatus)
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
            Text(text = "账号")
        },
        placeholder = { Text("请输入账号") },
        onValueChange = { onValueChange(loginUiState.copy(account = it)) },
    )
    OutlinedTextField(
        modifier = fullWidthModifier,
        value = loginUiState.password,
        label = {
            Text(text = "密码")
        },
        placeholder = { Text("请输入密码") },
        onValueChange = { onValueChange(loginUiState.copy(password = it)) },
        // 设置为密码模式
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )

    OutlinedTextField(
        modifier = fullWidthModifier,
        value = loginUiState.passRepeat,
        label = {
            Text(text = "验证密码")
        },
        placeholder = { Text("注册请输入两次密码") },
        onValueChange = { onValueChange(loginUiState.copy(passRepeat = it)) },
        // 设置为密码模式
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )

}

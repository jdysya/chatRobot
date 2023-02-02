package com.yx.chatrobot.ui.login

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.yx.chatrobot.data.LoginUiState
import com.yx.chatrobot.data.entity.User
import com.yx.chatrobot.data.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var loginUiState by mutableStateOf(LoginUiState())
        private set

    var currentStatus by mutableStateOf("待登录") // 记录当前的状态
        private set


    fun updateUiState(newLoginUiState: LoginUiState) {
        loginUiState = newLoginUiState.copy()
    }

    fun login(
        navigateToHome: (Int) -> Unit,
        errorAlert: () -> Unit
    ) {
        viewModelScope.launch {
            val user = async {
                userRepository.getUserByAccount(
                    loginUiState.account,
                    encode(loginUiState.password)
                )
            }
            if (user.await().id != 0) {
                currentStatus = "登录成功"
                navigateToHome(user.await().id)
            } else {
                currentStatus = "账号或密码错误"
                errorAlert()
            }
        }
    }

    fun register() {
        if (isLoginValid()) {
            if (loginUiState.password == loginUiState.passRepeat) {
                viewModelScope.launch {
                    val isRepeat = async { userRepository.isExistSameAccount(loginUiState.account) }
                    if (!isRepeat.await()) {
                        userRepository.insert(
                            User(
                                0,
                                "用户",
                                loginUiState.account,
                                encode(loginUiState.password),
                                "这个人很懒，没有签名"
                            )
                        )
                        currentStatus = "注册成功"
                    } else {
                        currentStatus = "当前账号已被使用"
                    }


                }
            } else {
                currentStatus = "两次输入的密码不一致"
            }

        } else {
            currentStatus = "没有输入两次密码或没有输入账号"
        }

    }

    fun isLoginValid(): Boolean {
        return !(loginUiState.account.isEmpty() || loginUiState.account.isEmpty())
    }

    fun isRegisterValid(): Boolean {
        return !(loginUiState.account.isEmpty()
                || loginUiState.account.isEmpty()
                || loginUiState.passRepeat.isEmpty())
    }

    /**
     * 将用户输入的密码实时的进行 MD5 加密，保证安全
     */
    fun encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            var sb: StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}


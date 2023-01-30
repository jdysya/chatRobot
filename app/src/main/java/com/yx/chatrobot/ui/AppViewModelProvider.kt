package com.yx.chatrobot.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yx.chatrobot.ChatApplication
import com.yx.chatrobot.MainViewModel
import com.yx.chatrobot.ui.login.LoginViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(
                this.createSavedStateHandle(),
                chatApplication().container.messageRepository,
                chatApplication().container.userRepository
            )
        }
        initializer {
            LoginViewModel(
                chatApplication().container.userRepository
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.chatApplication(): ChatApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ChatApplication)
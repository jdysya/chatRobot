package com.yx.chatrobot.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun ChatRobotBar(title: String) {
    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.h5,
                text = title
            )
        },

        )
}
package com.yx.chatrobot.ui.help

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yx.chatrobot.R

@Composable
fun ChatHelpScreen() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        item {
            Text(text = "一、介绍", fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.help_text))
            Text(text = "二、研发", fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.help_author))
            Text(text = "三、致谢", fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.help_thank))
        }
    }
}
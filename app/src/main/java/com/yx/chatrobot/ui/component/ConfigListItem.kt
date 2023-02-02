package com.yx.chatrobot.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yx.chatrobot.data.ConfigUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfigListItem(
    title: String,
    onButtonClick: () -> Unit,
    description: String,
    value:String
) {
    ListItem(
        text = { Text(text = title) },
        secondaryText = { Text(text = description) },
        trailing = {
            Button(
                onClick = onButtonClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(text = value)
                    Icon(
                        imageVector = Icons.Default.Edit,
                        modifier = Modifier.padding(end = 4.dp),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
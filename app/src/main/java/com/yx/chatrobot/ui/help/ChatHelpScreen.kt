package com.yx.chatrobot.ui.help

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yx.chatrobot.R
import com.yx.chatrobot.ui.component.ChatHeader

@Composable
fun ChatHelpScreen() {
    LazyColumn(
    ) {
        item {
            ChatHeader(text = "一、介绍")
            val annotatedLinkString: AnnotatedString = buildAnnotatedString {

                val str = stringResource(R.string.help_text)
                val startIndex = str.indexOf("官方文档")
                val endIndex = startIndex + 4
                append(str)
                addStyle(
                    style = SpanStyle(
                        color = Color(0xff64B5F6),
                        textDecoration = TextDecoration.Underline
                    ), start = startIndex, end = endIndex
                )
                addStringAnnotation(
                    tag = "URL",
                    annotation = "https://platform.openai.com/examples",
                    start = startIndex,
                    end = endIndex
                )

            }
            val uriHandler: UriHandler = LocalUriHandler.current
            ClickableText(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                text = annotatedLinkString,
                onClick = {
                    annotatedLinkString
                        .getStringAnnotations(it, it)
                        .firstOrNull()?.let { stringAnnotation ->
                            println("🔥 Clicked: $it, item: ${stringAnnotation.item}")
                            uriHandler.openUri(stringAnnotation.item)
                        }
                }
            )
        }
        item {
            ChatHeader(text = "二、研发")
            Text(
                text = stringResource(R.string.help_author))
        }
        item {
            ChatHeader(text = "三、致谢")
            Text(text = stringResource(R.string.help_thank))
        }
    }
}
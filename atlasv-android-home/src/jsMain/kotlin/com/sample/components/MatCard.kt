package com.sample.components

import androidx.compose.runtime.Composable
import com.sample.style.CommonStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun CardTitle(text: String) {
    Div(
        attrs = {
            classes(CommonStyles.cardTitle)
        },
        content = {
            Text(text)
        }
    )
}
package com.sample.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun Divider(height: Int) {
    Div(
        attrs = {
            style { height(height.px) }
        }
    )
}
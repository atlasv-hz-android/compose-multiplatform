package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.dom.Div

@Composable
fun Divider(height: CSSNumeric, color: CSSColorValue = Color.transparent) {
    Div(
        attrs = {
            style {
                height(height)
                backgroundColor(color)
            }
        }
    )
}
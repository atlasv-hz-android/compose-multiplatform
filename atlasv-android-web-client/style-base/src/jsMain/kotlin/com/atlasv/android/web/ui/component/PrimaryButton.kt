package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun PrimaryButton(text: String, enabled: Boolean, onClick: () -> Unit, customStyle: StyleScope.() -> Unit = {}) {
    Div(
        attrs = {
            if (enabled) {
                classes(CommonStyles.p100HoverGreen)
            }
        },
        content = {
            Div(
                attrs = {
                    classes(CommonStyles.primaryButton)
                    style {
                        if (!enabled) {
                            backgroundColor(Color.darkgray)
                            color(Color.lightgray)
                        }
                        customStyle()
                    }
                    onClick {
                        if (enabled) {
                            onClick()
                        }
                    }
                },
                content = {
                    Text(text)
                }
            )
        }
    )
}
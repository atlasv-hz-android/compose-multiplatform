package com.atlasv.android.web.ui.components

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun PrimaryButton(text: String, link: String) {
    Div(
        attrs = {
            classes(CommonStyles.p100HoverGreen)
        },
        content = {
            Div(
                attrs = {
                    classes(CommonStyles.primaryButton)
                    style {
                        width(98.percent)
                    }
                    onClick {
                        window.open(
                            url = link,
                            target = ATarget.Blank.targetStr
                        )
                    }
                },
                content = {
                    Text(text)
                }
            )
        }
    )
}
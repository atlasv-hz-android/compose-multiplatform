package com.atlasv.android.web.ui.component.dialog

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div

/**
 * Created by weiping on 2025/4/11
 */
@Composable
fun ModalDialog(content: @Composable () -> Unit) {
    Div(
        attrs = {
            style {
                position(Position.Fixed)
                top(0.px)
                left(0.px)
                width(100.percent)
                height(100.percent)
                backgroundColor(rgba(0, 0, 0, 0.5)) // 半透明黑色背景
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
                property("zIndex", 1000)
            }
        }
    ) {
        Div(
            attrs = {
                style {
                    backgroundColor(Color.white)
                    padding(20.px)
                    borderRadius(12.px)
                    property("box-shadow", "0px 4px 10px ${rgba(0, 0, 0, .2)}")
                }
            }
        ) {
            content()
        }
    }
}
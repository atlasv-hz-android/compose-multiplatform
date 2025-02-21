package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.ui.component.MaterialCardGrid
import com.atlasv.android.web.ui.style.CommonStyles
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    Style(CommonStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                paddingTop(20.px)
            }
        },
        content = {
            FunctionCardsContainer()
        }
    )
}

@Composable
private fun FunctionCardsContainer() {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
            classes(CommonStyles.justifyContentCenter)
        },
        content = {
            FunctionCards()
        }
    )
}

@Composable
private fun FunctionCards() {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
            classes(CommonStyles.p70)
        },
        content = {
            MaterialCardGrid(
                content = {
                    Text(value = "性能监控")
                },
                onClick = {
                    window.open(url = "${HttpEngine.baseUrl}perf-overview", target = ATarget.Blank.targetStr)
                }
            )
            MaterialCardGrid(
                content = {
                    Text(value = "文件上传")
                },
                onClick = {
                    window.open(url = "${HttpEngine.baseUrl}upload_file_page", target = ATarget.Blank.targetStr)
                }
            )
        }
    )
}


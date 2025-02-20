package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

/**
 * Created by weiping on 2025/2/17
 */

@Composable
fun MaterialCardGrid(content: ContentBuilder<HTMLDivElement>, onClick: () -> Unit) {
    Div(
        attrs = {
            classes(CommonStyles.p33)
        },
        content = {
            Div(
                attrs = {
                    classes(CommonStyles.cardHover)
                    style {
                        padding(6.px)
                    }
                    onClick {
                        onClick()
                    }
                },
                content = {
                    Div(
                        attrs = {
                            classes(CommonStyles.matCard)
                        },
                        content = content
                    )
                },
            )
        }
    )
}
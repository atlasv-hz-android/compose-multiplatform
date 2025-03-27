package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.ui.component.tab.TabContainer
import com.atlasv.android.web.ui.model.TabModel
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TabStyle
import com.atlasv.android.web.ui.style.TextStyles
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    Style(CommonStyles)
    Style(TextStyles)
    Style(TabStyle)
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
        },
        content = {
            TabContainer(
                models =
                    AppEnum.entries.map {
                        TabModel(
                            text = it.name,
                            id = it.ordinal
                        )
                    })
        }
    )
}

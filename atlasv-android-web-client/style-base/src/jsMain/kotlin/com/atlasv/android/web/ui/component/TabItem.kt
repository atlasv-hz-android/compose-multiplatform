package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/26
 */

@Composable
fun TabItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Div(
        attrs = {
            classes(if (selected) CommonStyles.tabItem else CommonStyles.tabItemUnSelected)
            onClick { onClick() }
        },
        content = {
            Text(
                value = text
            )
        })
}
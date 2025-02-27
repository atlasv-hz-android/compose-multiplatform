package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/26
 */

@Composable
fun TabItem(text: String) {
    Div(
        attrs = {
            classes(CommonStyles.tabItem)
        },
        content = {
            Text(
                value = text
            )
        })
}
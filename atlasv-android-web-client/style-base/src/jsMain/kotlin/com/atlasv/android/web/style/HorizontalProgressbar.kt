package com.atlasv.android.web.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Progress

/**
 * weiping@atlasv.com
 * 2022/8/18
 */
object HorizontalProgressbar {
    @Composable
    fun render(loading: Boolean) {
        if (!loading) {
            return
        }
        Div(
            attrs = {
                classes(CommonStyles.horizontalProgressBarContainer)
            },
            content = {
                Progress(
                    attrs = {
                        classes(CommonStyles.progressHorizontalP80)
                    }
                )
            }
        )
    }
}
package com.atlasv.android.web.ui.style

import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.px

/**
 * Created by weiping on 2025/2/11
 */
object TextStyles : StyleSheet() {
    val text1 by style {
        color(CommonColors.mainTextColor)
        fontSize(18.px)
    }

    val text2 by style {
        color(CommonColors.mainTextColor)
        fontSize(16.px)
    }

    val text3 by style {
        color(CommonColors.mainTextColor)
        fontSize(14.px)
    }

    val subText by style {
        color(CommonColors.subTextColor)
        fontSize(13.px)
    }

    val errorTip by style {
        color(CommonColors.red)
        fontSize(15.px)
    }

    val textBlue by style {
        color(Color.blue)
        fontSize(14.px)
    }
}
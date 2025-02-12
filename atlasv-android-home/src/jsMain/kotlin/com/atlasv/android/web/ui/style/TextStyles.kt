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
        color(Color.black)
        fontSize(24.px)
    }

    val text2 by style {
        color(Color.black)
        fontSize(20.px)
    }

    val text3 by style {
        color(Color("#333333"))
        fontSize(18.px)
    }

    val text4 by style {
        color(Color("#333333"))
        fontSize(16.px)
    }

    val text4Blue by style {
        color(Color.blue)
        fontSize(16.px)
    }


    val subText by style {
        color(Color("#555555"))
        fontSize(16.px)
    }

    val subText2 by style {
        color(Color("#555555"))
        fontSize(14.px)
    }


    val subText3 by style {
        color(Color("#555555"))
        fontSize(12.px)
    }
}
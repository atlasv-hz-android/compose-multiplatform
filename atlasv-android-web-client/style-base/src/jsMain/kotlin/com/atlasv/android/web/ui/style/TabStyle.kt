package com.atlasv.android.web.ui.style

import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.borderWidth
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.duration
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.transitions
import org.jetbrains.compose.web.css.width

/**
 * Created by weiping on 2025/3/27
 */
@OptIn(ExperimentalComposeWebApi::class)
object TabStyle : StyleSheet() {
    val tabContainer by style {
        margin(20.px)
        width(70.percent)
        backgroundColor(Color.white)
        borderRadius(8.px)
        property("box-shadow", "0 4px 8px ${rgba(0, 0, 0, .1)}")
    }

    val tabHeader by style {
        display(DisplayStyle.Flex)
        border {
            bottom(1.px)
            color(Color("#eee"))
        }
    }

    val tabButton by style {
        padding(12.px, 16.px)
        backgroundColor(Color.transparent)
        cursor("pointer")
        flex(1)
        textAlign("center")
        color(Color("#666"))
        transitions {
            "width" { duration(0.3.s) }
            defaultTimingFunction(AnimationTimingFunction.Ease)
            properties("color")
        }
        property("border", "none")
    }
    val tabButtonActive by style {
        padding(12.px, 16.px)
        backgroundColor(Color.transparent)
        cursor("pointer")
        flex(1)
        textAlign("center")
        color(CommonColors.primaryBlue)
        transitions {
            "width" { duration(1.s) }
            defaultTimingFunction(AnimationTimingFunction.Ease)
            properties("color")
        }
        border {
            color(CommonColors.primaryBlue)
            style(LineStyle.Solid)
        }
        borderWidth(
            top = 0.px,
            left = 0.px,
            right = 0.px,
            bottom = 2.px
        )
    }

    val tabContent by style {
        padding(20.px)
    }
}
package com.atlasv.android.web.style

import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.flexFlow
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.width

/**
 * weiping@atlasv.com
 * 2022/8/18
 */
object CommonStyles : StyleSheet() {

    val borderContainer by style {
        borderRadius(3.px)
        border {
            color(Color.darkgray)
            width(1.px)
            style(LineStyle.Solid)
        }
        margin(10.px)
        padding(10.px)
        backgroundColor(Color.white)
    }

    val horizontalFlow by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        flexFlow(FlexDirection.Row, FlexWrap.Wrap)
    }

    val horizontal by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        alignItems(AlignItems.Center)
    }

    val vertical by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
    }

    val verticalFlow by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        flexWrap(FlexWrap.Wrap)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
    }

    val justifyContentCenter by style {
        justifyContent(JustifyContent.Center)
    }

    val alignItemsCenter by style {
        alignItems(AlignItems.Center)
    }

    val horizontalProgressBarContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        paddingTop(16.px)
        paddingBottom(16.px)
    }

    val progressHorizontalP80 by style {
        width(80.percent)
    }

    val nextButtonContainer by style {
        fontSize(120.percent)
        fontWeight("bold")
        color(CommonColors.newGreen)
        paddingTop(10.px)
        paddingBottom(10.px)
    }

    val p33 by style {
        width(33.33.percent)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        this.hover {
            backgroundColor(Color("#f0f0f0"))
        }
    }

    val p100 by style {
        width(100.percent)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
    }

    val p70 by style {
        margin(10.px)
        width(70.percent)
        maxWidth(1000.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.SpaceAround)
    }

    val p100HoverGreen by style {
        width(100.percent)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        this.hover {
            backgroundColor(CommonColors.primaryBlueDark)
        }
    }

    val matCard by style {
        boxSizing("border-box")
        borderRadius(12.px)
        border {
            color(Color.lightgray)
            width(1.px)
            style(LineStyle.Solid)
        }
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        padding(20.px, 20.px, 12.px)
        height(208.px)
        width(98.percent)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        backgroundColor(Color.white)
        property("box-shadow", "1px 1px ${rgba(0, 0, 0, .2)}")
    }
    val cardTitle by style {
        fontSize(200.percent)
        color(Color.black)
        justifyContent(JustifyContent.Center)
        fontWeight(500)
    }

    val primaryButton by style {
        boxSizing("border-box")
        borderRadius(12.px)
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        height(56.px)
        width(100.percent)
        backgroundColor(CommonColors.primaryBlue)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        fontSize(20.px)
        fontWeight(500)
        color(Color.white)
        property("box-shadow", "1px 1px ${rgba(0, 0, 0, .2)}")
    }
}
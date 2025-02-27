package com.atlasv.android.web.ui.style

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
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
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
    }

    val p10 by style {
        width(10.percent)
    }

    val cardHover by style {
        this.hover {
            backgroundColor(CommonColors.cardHover)
        }
    }

    val p70 by style {
        width(70.percent)
    }

    val p100HoverGreen by style {
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

    val matCardSmall by style {
        boxSizing("border-box")
        borderRadius(12.px)
        border {
            color(Color.lightgray)
            width(1.px)
            style(LineStyle.Solid)
        }
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        backgroundColor(Color.white)
        property("box-shadow", "1px 1px ${rgba(0, 0, 0, .2)}")
    }

    val card by style {
        boxSizing("border-box")
        borderRadius(12.px)
        border {
            color(Color.lightgray)
            width(1.px)
            style(LineStyle.Solid)
        }
        backgroundColor(Color.white)
        property("box-shadow", "1px 1px ${rgba(0, 0, 0, .2)}")
    }

    val primaryButton by style {
        boxSizing("border-box")
        borderRadius(12.px)
        backgroundColor(CommonColors.primaryBlue)
        fontSize(14.px)
        fontWeight(500)
        color(Color.white)
        paddingTop(4.px)
        paddingBottom(4.px)
        paddingLeft(16.px)
        paddingRight(16.px)
        property("box-shadow", "1px 1px ${rgba(0, 0, 0, .2)}")
    }

    val checkBox by style {
        width(20.px)
        height(20.px)
    }

    val tabItem by style {
        borderRadius(99.px)
        backgroundColor(CommonColors.primaryBlue)
        color(Color.white)
        fontSize(13.px)
        paddingLeft(12.px)
        paddingRight(12.px)
        paddingTop(3.px)
        paddingBottom(3.px)
    }

    val tabItemUnSelected by style {
        borderRadius(99.px)
        border {
            color(Color.darkgray)
            width(1.px)
            style(LineStyle.Solid)
        }
        backgroundColor(Color.white)
        color(Color.black)
        fontSize(13.px)
        paddingLeft(12.px)
        paddingRight(12.px)
        paddingTop(3.px)
        paddingBottom(3.px)
    }
}
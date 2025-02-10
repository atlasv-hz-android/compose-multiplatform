package com.sample.style

import org.jetbrains.compose.web.css.*

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
            backgroundColor(Color.transparent)
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
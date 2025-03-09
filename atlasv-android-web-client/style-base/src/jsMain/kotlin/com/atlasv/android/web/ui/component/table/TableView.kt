package com.atlasv.android.web.ui.component.table

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.borderWidth
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

/**
 * Created by weiping on 2025/3/10
 */

@Composable
fun TableRowView(cells: List<TableCellModel>, smallTextMode: Boolean, isHeader: Boolean) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
            if (isHeader) {
                style {
                    backgroundColor(Color.lightgray)
                    borderRadius(
                        topLeft = 12.px,
                        topRight = 12.px,
                        bottomLeft = 0.px,
                        bottomRight = 0.px
                    )
                }
            }
        },
        content = {
            cells.forEachIndexed { index, item ->
                TableCellView(
                    content = {
                        Text(item.text)
                    }, fontWeight = if (item.isHeaderCell) 500 else 400, isEndCol = index == cells.lastIndex,
                    screenshotMode = smallTextMode
                )
            }
        }
    )
}

@Composable
fun TableCellView(
    isEndCol: Boolean = false,
    content: ContentBuilder<HTMLDivElement>,
    fontWeight: Int = 400,
    backgroundColor: CSSColorValue = Color.transparent,
    screenshotMode: Boolean
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.p10, CommonStyles.justifyContentCenter)
            style {
                fontSize(if (screenshotMode) 11.px else 13.px)
                fontWeight(fontWeight)
                border {
                    color(CommonColors.dividerColorDark)
                    style(LineStyle.Solid)
                    paddingTop(if (screenshotMode) 4.px else 6.px)
                    paddingBottom(6.px)
                }
                borderWidth(
                    top = 0.px,
                    right = if (isEndCol) 0.px else 1.px,
                    bottom = 0.px,
                    left = 0.px,
                )
                backgroundColor(backgroundColor)
            }
        },
        content = content
    )
}
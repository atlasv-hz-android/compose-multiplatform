package com.atlasv.android.web.ui.component.table

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.model.TableModel
import com.atlasv.android.web.ui.model.TableRowModel
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
fun TableView(model: TableModel, smallTextMode: Boolean) {
    Div(
        attrs = {
            classes(CommonStyles.p70, CommonStyles.card, CommonStyles.vertical)
            style {
                paddingTop(0.px)
                paddingBottom(0.px)
            }
        },
        content = {
            model.rows.forEachIndexed { index, rowModel ->
                if (!rowModel.isHeader) {
                    VerticalDivider(
                        height = 1.px,
                        color = CommonColors.dividerColorDark
                    )
                }
                TableRowView(rowModel, smallTextMode = smallTextMode, isHeader = rowModel.isHeader)
                if (rowModel.isGroupEnd && index != model.rows.lastIndex) {
                    VerticalDivider(
                        height = if (smallTextMode) 1.5.px else 2.px,
                        color = CommonColors.dividerColorDark
                    )
                }
            }
        }
    )
}

@Composable
fun TableRowView(rowModel: TableRowModel, smallTextMode: Boolean, isHeader: Boolean) {
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
            rowModel.cells.forEachIndexed { index, item ->
                TableCellView(
                    content = {
                        Text(item.text)
                    }, fontWeight = if (item.isHeaderCell) 500 else 400, isEndCol = index == rowModel.cells.lastIndex,
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
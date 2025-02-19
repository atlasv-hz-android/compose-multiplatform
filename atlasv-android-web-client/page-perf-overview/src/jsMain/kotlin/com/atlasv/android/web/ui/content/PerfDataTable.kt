package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.VitalPerfRateModel
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import com.atlasv.android.web.ui.component.Divider
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
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

/**
 * Created by weiping on 2025/2/18
 */

@Composable
fun PerfDataTable(anrData: List<VitalPerfRateResponse>, crashData: VitalPerfRateResponse?) {
    val crashRows = crashData?.rows?.sortedByDescending {
        it.startTime.getSortWeight()
    }.orEmpty()
    val isEmpty = anrData.all { it.rows.isEmpty() } && crashData?.rows?.isNotEmpty() != true
    Div(
        attrs = {
            classes(CommonStyles.p70, CommonStyles.card, CommonStyles.vertical)
            style {
                paddingTop(0.px)
                paddingBottom(0.px)
            }
        },
        content = {
            anrData.firstOrNull()?.rows?.also { anrRows ->
                PerfDataHeadRow(anrRows)
                Divider(height = 1.px, color = CommonColors.dividerColorDark)
            }

            anrData.forEach { anrResponse ->
                PerfDataRow(anrResponse.rows, perfType = "ANR")
            }
            if (crashRows.isNotEmpty()) {
                PerfDataRow(crashRows, perfType = "Crash")
            }

            if (isEmpty) {
                LoadingView()
            }
        }
    )
}

@Composable
private fun LoadingView() {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter, CommonStyles.alignItemsCenter)
            style {
                height(100.px)
            }
        },
        content = {
            Text("数据加载中...")
        }
    )
}

@Composable
private fun PerfDataHeadRow(rows: List<VitalPerfRateModel>) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
            style {
                backgroundColor(Color.lightgray)
                borderRadius(
                    topLeft = 12.px,
                    topRight = 12.px,
                    bottomLeft = 0.px,
                    bottomRight = 0.px
                )
            }
        },
        content = {
            PerfDataGridItem(
                content = {
                    Text("App")
                }, fontWeight = 500
            )
            PerfDataGridItem(content = {
                Text("指标")
            }, fontWeight = 500)
            PerfDataGridItem(content = {
                Text("设备维度")
            }, fontWeight = 500)
            rows.forEachIndexed { index, item ->
                PerfDataGridItem(content = {
                    Text("${item.startTime.month}-${item.startTime.day}")
                }, fontWeight = 500, isEndCol = index == rows.lastIndex)
            }
        }
    )

}

@Composable
private fun PerfDataRow(rows: List<VitalPerfRateModel>, perfType: String) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
        },
        content = {
            PerfDataGridItem(
                content = {
                    Text("ShotCut")
                },
                fontWeight = 500
            )
            PerfDataGridItem(content = {
                Text(perfType)
            })
            PerfDataGridItem(content = {
                Text(rows.firstOrNull()?.dimensions?.firstOrNull()?.valueLabel ?: "全部机型")
            })
            rows.forEachIndexed { index, model ->
                PerfDataGridItem(content = {
                    Text(model.metrics.firstOrNull()?.decimalValue?.asPercent()?.toString().orEmpty())
                }, isEndCol = index == rows.lastIndex)
            }
        }
    )
}

@Composable
private fun PerfDataGridItem(
    isEndCol: Boolean = false,
    content: ContentBuilder<HTMLDivElement>,
    fontWeight: Int = 400,
    backgroundColor: CSSColorValue = Color.transparent
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.p10, CommonStyles.justifyContentCenter)
            style {
                fontSize(15.px)
                fontWeight(fontWeight)
                border {
                    color(CommonColors.dividerColorDark)
                    style(LineStyle.Solid)
                    paddingTop(8.px)
                    paddingBottom(8.px)
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
package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.data.model.VitalPerfRateModel
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
fun PerfDataTable(appPerfDataList: List<AppPerfData>, simplifyMode: Boolean) {
    val isEmpty = appPerfDataList.isEmpty()
    Div(
        attrs = {
            classes(CommonStyles.p70, CommonStyles.card, CommonStyles.vertical)
            style {
                paddingTop(0.px)
                paddingBottom(0.px)
            }
        },
        content = {
            HeadRow(appPerfDataList)
            appPerfDataList.forEachIndexed { index, appPerfDataItem ->
                PerfDataContentView(appPerfDataItem, simplifyMode)
                if (index != appPerfDataList.lastIndex) {
                    Divider(height = 4.px, color = CommonColors.dividerColorDark)
                }
            }
            if (isEmpty) {
                LoadingView()
            }
        }
    )
}

@Composable
private fun PerfDataContentView(appPerfDataItem: AppPerfData, simplifyMode: Boolean) {
    val anrData = appPerfDataItem.getAnrData(simplifyMode)
    val crashData = appPerfDataItem.getCrashData(simplifyMode)
    anrData.forEachIndexed { index, anrResponse ->
        PerfDataRow(
            appName = anrResponse.appNickname,
            anrResponse.rows,
            perfType = "ANR",
            isFirstRow = index == 0,
            isLastRow = index == anrData.lastIndex
        )
    }
    if (anrData.isNotEmpty() && crashData.isNotEmpty()) {
        Divider(height = 1.5.px, color = CommonColors.dividerColorDark)
    }
    crashData.forEachIndexed { index, crashResponse ->
        PerfDataRow(
            appName = crashResponse.appNickname,
            crashResponse.rows,
            perfType = "Crash",
            isFirstRow = index == 0,
            isLastRow = index == crashData.lastIndex
        )
    }
}

@Composable
private fun HeadRow(appPerfData: List<AppPerfData>) {
    appPerfData.firstOrNull()?.anrNoDimensionData?.rows?.also { anrRows ->
        PerfDataHeadRow(anrRows)
        Divider(height = 1.px, color = CommonColors.dividerColorDark)
    }
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
private fun PerfDataRow(
    appName: String,
    rows: List<VitalPerfRateModel>,
    perfType: String,
    isFirstRow: Boolean,
    isLastRow: Boolean
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
        },
        content = {
            PerfDataGridItem(
                content = {
                    Div(
                        attrs = {
                            style {
                                color(if (isFirstRow) Color.black else Color.transparent)
                            }
                        },
                        content = {
                            Text(appName)
                        }
                    )
                },
                fontWeight = 500
            )
            PerfDataGridItem(content = {
                Text(perfType)
            }, hasBottomBorder = !isLastRow)
            PerfDataGridItem(content = {
                Text(rows.firstOrNull()?.dimensions?.firstOrNull()?.valueLabel ?: "全部机型")
            }, hasBottomBorder = !isLastRow)
            rows.forEachIndexed { index, model ->
                PerfDataGridItem(content = {
                    Text(model.metrics.firstOrNull()?.decimalValue?.asPercent()?.toString().orEmpty())
                }, isEndCol = index == rows.lastIndex, hasBottomBorder = !isLastRow)
            }
        }
    )
}

@Composable
private fun PerfDataGridItem(
    isEndCol: Boolean = false,
    hasBottomBorder: Boolean = false,
    content: ContentBuilder<HTMLDivElement>,
    fontWeight: Int = 400,
    backgroundColor: CSSColorValue = Color.transparent
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.p10, CommonStyles.justifyContentCenter)
            style {
                fontSize(13.px)
                fontWeight(fontWeight)
                border {
                    color(CommonColors.dividerColorDark)
                    style(LineStyle.Solid)
                    paddingTop(6.px)
                    paddingBottom(6.px)
                }
                borderWidth(
                    top = 0.px,
                    right = if (isEndCol) 0.px else 1.px,
                    bottom = if (hasBottomBorder) 1.px else 0.px,
                    left = 0.px,
                )
                backgroundColor(backgroundColor)
            }
        },
        content = content
    )
}
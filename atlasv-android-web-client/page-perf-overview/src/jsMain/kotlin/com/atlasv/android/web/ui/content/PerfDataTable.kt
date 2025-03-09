package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.data.model.VitalPerfRateModel
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.table.TableCellView
import com.atlasv.android.web.ui.component.table.TableRowView
import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/18
 */

@Composable
fun PerfDataTable(
    appPerfDataList: List<AppPerfData>,
    simplifyMode: Boolean,
    screenshotMode: Boolean,
    loading: Boolean
) {
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
            HeadRow(appPerfDataList, screenshotMode)
            appPerfDataList.forEachIndexed { index, appPerfDataItem ->
                PerfDataContentView(appPerfDataItem, simplifyMode, screenshotMode)
                if (index != appPerfDataList.lastIndex) {
                    VerticalDivider(
                        height = if (screenshotMode) 2.5.px else 4.px,
                        color = CommonColors.dividerColorDark
                    )
                }
            }
            if (loading) {
                LoadingView(text = "数据加载中...")
            } else {
                if (isEmpty) {
                    LoadingView(text = "当前数据为空")
                }
            }
        }
    )
}

@Composable
private fun PerfDataContentView(appPerfDataItem: AppPerfData, simplifyMode: Boolean, screenshotMode: Boolean) {
    val anrData = appPerfDataItem.getAnrData(simplifyMode)
    val crashData = appPerfDataItem.getCrashData(simplifyMode)
    anrData.forEachIndexed { index, anrResponse ->
        PerfDataRow(
            appName = appPerfDataItem.appNickName,
            anrResponse.rows,
            perfType = "ANR",
            isFirstRow = index == 0,
            isLastRow = index == anrData.lastIndex,
            screenshotMode = screenshotMode
        )
    }
    if (anrData.isNotEmpty() && crashData.isNotEmpty()) {
        VerticalDivider(height = if (screenshotMode) 1.px else 1.5.px, color = CommonColors.dividerColorDark)
    }
    crashData.forEachIndexed { index, crashResponse ->
        PerfDataRow(
            appName = appPerfDataItem.appNickName,
            crashResponse.rows,
            perfType = "Crash",
            isFirstRow = index == 0,
            isLastRow = index == crashData.lastIndex,
            screenshotMode = screenshotMode
        )
    }
}

@Composable
private fun HeadRow(appPerfData: List<AppPerfData>, screenshotMode: Boolean) {
    appPerfData.firstOrNull()?.anrNoDimensionData?.rows?.also { anrRows ->
        PerfDataHeadRow(anrRows, screenshotMode)
        VerticalDivider(height = 1.px, color = CommonColors.dividerColorDark)
    }
}

@Composable
private fun LoadingView(text: String) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter, CommonStyles.alignItemsCenter)
            style {
                height(100.px)
            }
        },
        content = {
            Text(text)
        }
    )
}

@Composable
private fun PerfDataHeadRow(rows: List<VitalPerfRateModel>, screenshotMode: Boolean) {
    val headerCells = listOf(
        TableCellModel(
            text = "App", id = 0
        ), TableCellModel(
            text = "指标", id = 0
        ), TableCellModel(
            text = "设备维度", id = 0
        )
    ) + rows.map {
        it.asTableHeaderCellModel()
    }
    TableRowView(headerCells, smallTextMode = screenshotMode)
}

@Composable
private fun PerfDataRow(
    appName: String,
    rows: List<VitalPerfRateModel>,
    perfType: String,
    isFirstRow: Boolean,
    isLastRow: Boolean,
    screenshotMode: Boolean
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
        },
        content = {
            TableCellView(
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
                fontWeight = 500,
                screenshotMode = screenshotMode
            )
            TableCellView(
                content = {
                    Text(perfType)
                }, hasBottomBorder = !isLastRow,
                screenshotMode = screenshotMode
            )
            TableCellView(
                content = {
                    Text(rows.firstOrNull()?.dimensions?.firstOrNull()?.valueLabel ?: "全部机型")
                }, hasBottomBorder = !isLastRow,
                screenshotMode = screenshotMode
            )
            rows.forEachIndexed { index, model ->
                TableCellView(
                    content = {
                        Text(model.metrics.firstOrNull()?.decimalValue?.asPercent()?.toString().orEmpty())
                    }, isEndCol = index == rows.lastIndex, hasBottomBorder = !isLastRow,
                    screenshotMode = screenshotMode
                )
            }
        }
    )
}
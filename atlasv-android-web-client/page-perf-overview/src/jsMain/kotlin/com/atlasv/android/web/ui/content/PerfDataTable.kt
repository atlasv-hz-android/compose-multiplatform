package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.table.TableRowView
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
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
            appPerfDataList.firstOrNull()?.createTableHeadRowModel()?.also { headRowModel ->
                TableRowView(headRowModel, smallTextMode = screenshotMode, isHeader = true)
                VerticalDivider(height = 1.px, color = CommonColors.dividerColorDark)
            }
            appPerfDataList.forEachIndexed { index, appPerfDataItem ->
                PerfDataContentView(appPerfDataItem, simplifyMode, screenshotMode)
                if (index != appPerfDataList.lastIndex) {
                    VerticalDivider(
                        height = if (screenshotMode) 1.5.px else 2.px,
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
    val appName = appPerfDataItem.appNickName
    listOf(anrData to "ANR", crashData to "Crash").forEach { (data, perfType) ->
        val rowModels = data.map {
            it.asTableRowModel(appName = appName, perfType = perfType)
        }
        rowModels.forEach { rowModel ->
            VerticalDivider(
                height = 1.px,
                color = CommonColors.dividerColorDark
            )
            TableRowView(
                rowModel,
                smallTextMode = screenshotMode,
                isHeader = false
            )
        }
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

package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.data.repo.PerfRepo
import com.atlasv.android.web.ui.component.Checkbox
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.PrimaryButton
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.table.TableView
import com.atlasv.android.web.ui.model.TableModel
import com.atlasv.android.web.ui.style.CommonStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var simplifyMode by remember {
        mutableStateOf(true)
    }
    var smallTextMode by remember {
        mutableStateOf(false)
    }
    var appPerfDataList by remember {
        mutableStateOf<List<AppPerfData>>(emptyList())
    }
    var loading by remember {
        mutableStateOf(false)
    }
    Style(CommonStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
            style {
                paddingTop(40.px)
            }
        },
        content = {
            OptionsView(
                checked = simplifyMode,
                screenshotMode = smallTextMode,
                loading = loading,
                onSimplifyModeChange = {
                    simplifyMode = !simplifyMode
                },
                onScreenShotModeChange = {
                    smallTextMode = !smallTextMode
                },
                onClickRefresh = {
                    CoroutineScope(Dispatchers.Default).launch {
                        loading = true
                        appPerfDataList = PerfRepo.createPerfData(PerfRepo.instance.getAllVitalsData())
                        loading = false
                    }
                }
            )
            VerticalDivider(height = 12.px)
            PerfDataTable(appPerfDataList, simplifyMode, smallTextMode)
            VerticalDivider(height = 24.px)
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            loading = true
            appPerfDataList = PerfRepo.createPerfData(PerfRepo.instance.getAllVitalsData())
            loading = false
        }
    }
}

@Composable
fun PerfDataTable(
    appPerfDataList: List<AppPerfData>,
    simplifyMode: Boolean,
    smallTextMode: Boolean
) {
    val tableModel = TableModel(
        rows = listOfNotNull(
            appPerfDataList.firstOrNull()?.createTableHeadRowModel()
        ) + appPerfDataList.map { appPerfDataItem ->
            appPerfDataItem.createDataRowModels(simplifyMode)
        }.flatten(), spanCount = 11
    )
    TableView(model = tableModel, smallTextMode = smallTextMode)
}

@Composable
private fun OptionsView(
    checked: Boolean,
    screenshotMode: Boolean,
    loading: Boolean,
    onSimplifyModeChange: (Boolean) -> Unit,
    onScreenShotModeChange: (Boolean) -> Unit,
    onClickRefresh: () -> Unit
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.p70)
        },
        content = {
            Checkbox(
                checked = checked,
                onCheckedChange = onSimplifyModeChange,
                label = "精简模式"
            )
            HorizontalDivider(width = 10.px)
            Checkbox(
                checked = screenshotMode,
                onCheckedChange = onScreenShotModeChange,
                label = "小字模式"
            )
            HorizontalDivider(width = 20.px)
            PrimaryButton(
                text = "刷新",
                enabled = !loading,
                onClick = onClickRefresh,
            )
        }
    )
}

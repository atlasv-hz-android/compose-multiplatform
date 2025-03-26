package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.DiskReportDetail
import com.atlasv.android.web.data.repo.DiskReportRepo
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.MaterialCardGridSmall
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import io.ktor.http.encodeURLQueryComponent
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    var _reportDetail by remember {
        mutableStateOf<DiskReportDetail?>(null)
    }
    val reportDetail = _reportDetail?.takeIf { it.appPackage == currentApp?.packageName }
    var loading by remember {
        mutableStateOf(false)
    }
    Style(CommonStyles)
    Style(TextStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                paddingLeft(16.px)
                paddingRight(16.px)
            }
        },
        content = {
            VerticalDivider(height = 24.px)
            Reports(currentApp, onClickReport = { priority ->
                CoroutineScope(Dispatchers.Default).launch {
                    loading = true
                    _reportDetail = DiskReportRepo.instance.listReportFiles(currentApp?.packageName, priority)
                    loading = false
                }
            },
                onClickApp = {
                    if (loading) {
                        return@Reports
                    }
                    currentApp = it
                })
            if (loading) {
                Text("正在加载数据...")
            } else {
                ReportDetailView(reportDetail)
            }
        }
    )
}

@Composable
private fun Reports(
    currentApp: AppEnum?,
    onClickReport: (priority: Int) -> Unit,
    onClickApp: (AppEnum) -> Unit
) {
    val apps = listOf(
        AppEnum.Ins3, AppEnum.Ttd1, AppEnum.Ttd2, AppEnum.Fbd2
    )
    AppTabLayout(
        items = apps.map {
            TabItemData(text = it.name, id = it.ordinal)
        },
        onItemClick = { id ->
            onClickApp(AppEnum.entries.find { it.ordinal == id }!!)
        },
        selectedIndex = currentApp?.let {
            apps.indexOf(it)
        } ?: -1
    )
    VerticalDivider(height = 16.px)
    ReportPriorityItem(onClickReport = onClickReport)
}

@Composable
private fun ReportPriorityItem(onClickReport: (priority: Int) -> Unit) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            (0..3).forEach { priority ->
                MaterialCardGridSmall(
                    content = {
                        Div(
                            attrs = {
                                classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter)
                                style {
                                    paddingTop(4.px)
                                    paddingBottom(4.px)
                                    if (priority == 50) {
                                        color(CommonColors.red)
                                        fontWeight(500)
                                    }
                                }
                            },
                            content = {
                                Text("P${priority}")
                            }
                        )
                    },
                    onClick = {
                        onClickReport(priority)
                    }
                )
            }
        }
    )
}

@Composable
private fun ReportDetailView(detail: DiskReportDetail?) {
    detail ?: return
    detail.files.forEach { filePath ->
        Div(
            attrs = {
                classes(TextStyles.text2)
                onClick {
                    window.open(
                        url = "${HttpEngine.baseUrl}download_file?file_path=${filePath.encodeURLQueryComponent()}&app_package=${detail.appPackage}&bucket=${detail.bucket}"
                    )
                }
            },
            content = {
                Text("$filePath (查看)")
            }
        )
        VerticalDivider(height = 3.px)
    }
}
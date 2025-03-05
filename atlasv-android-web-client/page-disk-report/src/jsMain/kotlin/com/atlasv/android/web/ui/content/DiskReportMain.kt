package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.DiskReport
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
    var allAppData by remember { mutableStateOf<Map<AppEnum, List<DiskReport>>>(emptyMap()) }
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    val data: List<DiskReport> = currentApp?.let {
        allAppData[it]
    }.orEmpty()
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
            Reports(data, currentApp,
                onClickReport = {
                    CoroutineScope(Dispatchers.Default).launch {
                        loading = true
                        _reportDetail = DiskReportRepo.instance.listReportFiles(it)
                        loading = false
                    }
                },
                onClickApp = {
                    if (loading) {
                        return@Reports
                    }
                    currentApp = it
                    CoroutineScope(Dispatchers.Default).launch {
                        loading = true
                        if (allAppData[it] == null) {
                            allAppData =
                                allAppData + (it to DiskReportRepo.instance.getReports(currentApp)?.data.orEmpty())
                        }
                        loading = false
                    }
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
    reports: List<DiskReport>,
    currentApp: AppEnum?,
    onClickReport: (DiskReport) -> Unit,
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
    val versions: List<Pair<Int, String>> =
        reports.map { it.versionCode to it.versionName }.distinctBy { it.first }.sortedByDescending { it.first }
    versions.forEach { version ->
        ReportItem(version, reports.filter { it.versionCode == version.first }.sortedByDescending { it.sizeG },
            onClickReport = onClickReport
        )
        VerticalDivider(height = 20.px)
    }
}

@Composable
private fun ReportItem(version: Pair<Int, String>, reports: List<DiskReport>, onClickReport: (DiskReport) -> Unit) {
    val (versionCode, versionName) = version
    Div(
        attrs = {
            classes(TextStyles.text2)
        },
        content = {
            Text("$versionName($versionCode)")
        }
    )

    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            reports.forEach { report ->
                MaterialCardGridSmall(
                    content = {
                        Div(
                            attrs = {
                                classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter)
                                style {
                                    paddingTop(4.px)
                                    paddingBottom(4.px)
                                    if (report.sizeG >= 5) {
                                        color(CommonColors.red)
                                        fontWeight(500)
                                    }
                                }
                            },
                            content = {
                                Text("${report.sizeG}G(${report.reportCount}${if (report.reportCount >= 100) "+" else ""})")
                            }
                        )
                    },
                    onClick = {
                        onClickReport(report)
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
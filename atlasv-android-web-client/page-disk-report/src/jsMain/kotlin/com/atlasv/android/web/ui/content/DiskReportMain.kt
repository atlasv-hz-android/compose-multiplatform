package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.DiskReport
import com.atlasv.android.web.data.model.DiskReportDetail
import com.atlasv.android.web.data.repo.DiskReportRepo
import com.atlasv.android.web.ui.component.MaterialCardGridSmall
import com.atlasv.android.web.ui.component.TabItem
import com.atlasv.android.web.ui.component.VerticalDivider
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
    var data by remember { mutableStateOf<List<DiskReport>>(emptyList()) }
    var reportDetail by remember {
        mutableStateOf<DiskReportDetail?>(null)
    }
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
            Reports(data,
                onClick = {
                    CoroutineScope(Dispatchers.Default).launch {
                        loading = true
                        reportDetail = DiskReportRepo.instance.listReportFiles(it)
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
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            data = DiskReportRepo.instance.getReports()?.data.orEmpty()
        }
    }
}

@Composable
private fun Reports(reports: List<DiskReport>, onClick: (DiskReport) -> Unit) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            TabItem("Fbd2")
        }
    )
    VerticalDivider(height = 8.px)
    val versions: List<Pair<Int, String>> =
        reports.map { it.versionCode to it.versionName }.distinctBy { it.first }.sortedByDescending { it.first }
    versions.forEach { version ->
        ReportItem(version, reports.filter { it.versionCode == version.first }.sortedByDescending { it.sizeG },
            onClick = onClick
        )
        VerticalDivider(height = 20.px)
    }
}

@Composable
private fun ReportItem(version: Pair<Int, String>, reports: List<DiskReport>, onClick: (DiskReport) -> Unit) {
    val (versionCode, versionName) = version
    Div(
        attrs = {
            classes(TextStyles.text4)
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
                        onClick(report)
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
                classes(TextStyles.text4)
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
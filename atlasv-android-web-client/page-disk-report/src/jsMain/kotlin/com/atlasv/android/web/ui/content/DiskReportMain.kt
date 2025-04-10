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
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.tab.TabContainer
import com.atlasv.android.web.ui.model.TabModel
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TabStyle
import com.atlasv.android.web.ui.style.TextStyles
import io.ktor.http.encodeURLQueryComponent
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
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
    val cache by remember {
        mutableStateOf<MutableMap<String, DiskReportDetail>>(mutableMapOf())
    }
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    var _reportDetail by remember {
        mutableStateOf<DiskReportDetail?>(null)
    }
    val reportDetail = _reportDetail?.takeIf { it.appPackage == currentApp?.packageName }

    Style(CommonStyles)
    Style(TextStyles)
    Style(TabStyle)
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
        },
        content = {
            TabContainer(
                models =
                    AppEnum.entries.map {
                        TabModel(
                            text = it.name,
                            id = it.ordinal
                        )
                    }, onTabClick = { model ->
                    currentApp = AppEnum.entries.find { it.ordinal == model.id }
                    CoroutineScope(Dispatchers.Default).launch {
                        val packageName = currentApp?.packageName ?: return@launch
                        _reportDetail = cache[packageName]
                        if (_reportDetail != null) {
                            return@launch
                        }
                        _reportDetail = DiskReportRepo.instance.listReportFiles(currentApp?.packageName, 0)?.also {
                            cache[packageName] = it
                        }
                    }
                },
                tabContentBuilder = {
                    ReportDetailView(reportDetail)
                }, initIndex = -1
            )
        }
    )
}

@Composable
private fun ReportDetailView(detail: DiskReportDetail?) {
    detail ?: return
    detail.files.forEach { filePath ->
        Div(
            attrs = {
                classes(TextStyles.text3)
                onClick {
                    window.open(
                        url = "${HttpEngine.baseUrl}download_file?file_path=${filePath.encodeURLQueryComponent()}&app_package=${detail.appPackage}&bucket=${detail.bucket}"
                    )
                }
            },
            content = {
                Text("${filePath.substringAfterLast("/")} (查看)")
            }
        )
        VerticalDivider(height = 4.px)
    }
}

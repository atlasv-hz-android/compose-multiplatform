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
import com.atlasv.android.web.ui.component.Divider
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
    var appPerfDataList by remember {
        mutableStateOf<List<AppPerfData>>(emptyList())
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
                onSimplifyModeChange = {
                    simplifyMode = !simplifyMode
                }
            )
            Divider(height = 12.px)
            PerfDataTable(appPerfDataList, simplifyMode)
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            appPerfDataList = PerfRepo.createPerfData(PerfRepo.instance.getAllVitalsData())
        }
    }
}

@Composable
private fun OptionsView(checked: Boolean, onSimplifyModeChange: (Boolean) -> Unit) {
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
        }
    )
}

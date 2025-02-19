package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import com.atlasv.android.web.data.model.mergeByDistinctUsers
import com.atlasv.android.web.data.repo.PerfDimensionType
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
    var anrNoDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    var anrDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    val anrDimensionDataFlattened = anrDimensionData?.flattenByDimensions().orEmpty()
    val lowRamAnrData = anrDimensionDataFlattened.mergeByDistinctUsers(dimensionLabel = "低端机")
    val anrData: List<VitalPerfRateResponse> =
        if (!simplifyMode) {
            listOfNotNull(anrNoDimensionData) + anrDimensionDataFlattened
        } else {
            listOfNotNull(anrNoDimensionData) + listOfNotNull(
                lowRamAnrData
            )
        }

    var crashNoDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    var crashDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    val crashDimensionDataFlattened = crashDimensionData?.flattenByDimensions().orEmpty()
    val lowRamCrashData = crashDimensionDataFlattened.mergeByDistinctUsers(dimensionLabel = "低端机")
    val crashData = if (!simplifyMode) {
        listOfNotNull(crashNoDimensionData) + crashDimensionDataFlattened
    } else {
        listOfNotNull(crashNoDimensionData) + listOfNotNull(lowRamCrashData)
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
            PerfDataTable(anrData, crashData)
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                anrNoDimensionData = PerfRepo.instance.getAnr(
                    appPackage = AppEnum.ShotCut.packageName, dimension = null
                )?.sortedByDateDescending()
            }
            launch {
                anrDimensionData = PerfRepo.instance.getAnr(
                    appPackage = AppEnum.ShotCut.packageName,
                    dimension = PerfDimensionType.RamBucket
                )
            }
            launch {
                crashNoDimensionData =
                    PerfRepo.instance.getCrash(appPackage = AppEnum.ShotCut.packageName, dimension = null)
                        ?.sortedByDateDescending()
            }
            launch {
                crashDimensionData = PerfRepo.instance.getCrash(
                    appPackage = AppEnum.ShotCut.packageName,
                    dimension = PerfDimensionType.RamBucket
                )
            }
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

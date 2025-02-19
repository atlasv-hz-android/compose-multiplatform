package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import com.atlasv.android.web.data.repo.PerfDimensionType
import com.atlasv.android.web.data.repo.PerfRepo
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
    var anrNoDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    var anrDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    val anrData: List<VitalPerfRateResponse> =
        listOfNotNull(anrNoDimensionData) + anrDimensionData?.flatterByDimensions().orEmpty()
    var crashData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    var crashDimensionData by remember { mutableStateOf<VitalPerfRateResponse?>(null) }
    Style(CommonStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
            style {
                paddingTop(40.px)
            }
        },
        content = {
            PerfDataTable(anrData, crashData)
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                anrNoDimensionData = PerfRepo.instance.getAnr(dimension = null)?.sortedByDateDescending()
            }
            launch {
                anrDimensionData = PerfRepo.instance.getAnr(dimension = PerfDimensionType.RamBucket)
            }
            launch {
                crashData = PerfRepo.instance.getCrash(dimension = null)?.sortedByDateDescending()
            }
            launch {
                crashDimensionData = PerfRepo.instance.getCrash(dimension = PerfDimensionType.RamBucket)
            }
        }
    }
}

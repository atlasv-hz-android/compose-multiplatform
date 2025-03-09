package com.atlasv.android.web.data.model

import com.atlasv.android.web.common.data.model.DateModel
import com.atlasv.android.web.ui.model.TableCellModel
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateModel(
    val metrics: List<VitalPerfRateMetric>,
    val dimensions: List<PerfDimensionModel>?,
    val startTime: DateModel
) {
    fun getDistinctUsers(): Int {
        return metrics.find { it.metric == "distinctUsers" }?.decimalValue?.value?.toInt() ?: 0
    }

    fun getFirstMetricRate(): Float {
        return metrics.first().decimalValue.value.toFloat()
    }

    fun asTableHeaderCellModel(): TableCellModel {
        return TableCellModel(
            text = "${this.startTime.month}-${this.startTime.day}",
            id = 0, isHeaderCell = true
        )
    }

    fun asTableCellModel(): TableCellModel {
        return TableCellModel(
            text = this.metrics.firstOrNull()?.decimalValue?.asPercent()?.toString().orEmpty(),
            id = 0
        )
    }

}

fun VitalPerfRateModel?.asDimensionCell(): String {
    return this?.dimensions?.firstOrNull()?.valueLabel ?: "全部机型"
}


package com.atlasv.android.web.data.model

import com.atlasv.android.web.common.data.model.DateModel
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
}


package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateMetric(
    val decimalValue: ValueContainer,
    val metric: String
)


@Serializable
data class ValueContainer(val value: String) {
    fun asPercent(): Float {
        return (value.toFloat() * 10000).roundToInt() / 100f
    }
}
package com.atlasv.android.web.data.model

import com.atlasv.android.web.common.data.model.DateModel
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateModel(
    val metrics: List<VitalPerfRateMetric>,
    val startTime: DateModel
)



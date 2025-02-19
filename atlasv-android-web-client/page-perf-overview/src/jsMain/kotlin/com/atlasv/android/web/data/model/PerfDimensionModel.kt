package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/19
 */
@Serializable
data class PerfDimensionModel(val dimension: String, val int64Value: Int, val valueLabel: String)

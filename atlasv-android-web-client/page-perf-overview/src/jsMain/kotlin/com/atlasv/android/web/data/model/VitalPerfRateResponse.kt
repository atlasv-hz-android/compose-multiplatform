package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateResponse(val rows: List<VitalPerfRateModel>)
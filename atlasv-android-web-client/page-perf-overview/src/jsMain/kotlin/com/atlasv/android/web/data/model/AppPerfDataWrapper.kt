package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/20
 */
@Serializable
data class AppPerfDataWrapper(
    val appNickname: String,
    val appPackage: String,
    val data: VitalPerfRateResponse,
    val fileName: String
)

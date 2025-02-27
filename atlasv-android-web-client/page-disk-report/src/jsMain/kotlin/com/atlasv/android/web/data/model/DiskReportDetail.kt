package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/26
 */
@Serializable
data class DiskReportDetail(
    @SerialName("app_package") val appPackage: String,
    val bucket: String,
    val files: List<String>,
)

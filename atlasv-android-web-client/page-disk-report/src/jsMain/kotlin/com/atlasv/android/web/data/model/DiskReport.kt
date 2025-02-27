package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/26
 */
@Serializable
data class DiskReport(
    @SerialName("app_package") val appPackage: String,
    @SerialName("report_count") val reportCount: Int,
    @SerialName("size_g") val sizeG: Int,
    @SerialName("version_code") val versionCode: Int,
    @SerialName("version_name") val versionName: String,
)

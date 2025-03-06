package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/3/6
 */
@Serializable
data class QueryRecord(val uid: String, @SerialName("app_package") val appPackage: String)

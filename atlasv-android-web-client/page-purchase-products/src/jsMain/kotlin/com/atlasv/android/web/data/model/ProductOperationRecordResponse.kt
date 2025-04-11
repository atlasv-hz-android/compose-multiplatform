package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/13
 */
@Serializable
data class ProductOperationRecordResponse(
    val records: List<ProductOperationRecord>,
    @SerialName("app_package") val appPackage: String
)

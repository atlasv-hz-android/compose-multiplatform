package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/4/10
 */
@Serializable
data class ProductOperationRecord(
    @SerialName("product_id") val productId: String,
    @SerialName("operation_type") val operationType: String,
    val description: String,
    @SerialName("operation_user") val operationUser: String,
    @SerialName("create_at") val createAt: String
)

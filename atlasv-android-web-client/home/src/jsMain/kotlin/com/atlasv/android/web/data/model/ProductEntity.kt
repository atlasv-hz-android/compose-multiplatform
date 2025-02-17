package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/13
 */
@Serializable
data class ProductEntity(
    @SerialName("entitlement_id") val entitlementId: String,
    @SerialName("product_id") val productId: String,
    @SerialName("app_package_name") val appPackageName: String,
    @SerialName("app_platform") val appPlatform: String,
    @SerialName("offering_id") val offeringId: String,
    val iss: String,
)

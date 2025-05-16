package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/4/28
 */
@Serializable
data class VipUserInfo(
    val adid: String,
    val advertising_id: String,
    val campaign: String,
    val currency: String,
    val gclid: String,
    val idfa: String,
    val install_timestamp: Long,
    val introductory_price: Long,
    val ip: String,
    val is_in_trial_period: Boolean,
    val original_transaction_id: String,
    val package_name: String,
    val price: Float,
    val user_id: String,
)

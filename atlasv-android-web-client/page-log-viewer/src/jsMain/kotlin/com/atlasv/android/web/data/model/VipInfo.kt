package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/4/28
 */
@Serializable
data class VipInfo(
    val TransactionID: String,
    val auto_renew: Boolean,
    val auto_renew_status_change_date_ms: Long,
    val cancellation_date_ms: Long,
    val entitlement_id: String,
    val environment: String,
    val expires_date_ms: Long,
    val expires_date: String,
    val is_in_intro_offer_period: Boolean,
    val is_in_trial_period: Boolean,
    val iss: String,
    val original_transaction_id: String,
    val payment_state: Int,
    val product_identifier: String,
    val purchase_date: String,
    val purchase_date_ms: Long,
    val subscription_group_identifier: String,
    val transaction_id: String,
    val user_id: String,
    val web_order_line_item_id: String
)

package com.atlasv.android.web.common.data.model

import kotlinx.serialization.Serializable

/**
 *
 * [使用签名标头获取用户的身份](https://cloud.google.com/iap/docs/identity-howto?hl=zh-cn#getting_the_users_identity_with_signed_headers)
 *
 * Created by weiping on 2025/3/12
 */
@Serializable
data class IapUser(
    val email: String,
    val id: String
) {
    val shortEmail = email.substringAfter("accounts.google.com:")
}

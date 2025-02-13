package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/10
 */

@Serializable
data class UploadRecordData(
    val code: Int,
    val message: String?,
    val items: List<UploadRecordItem>
) {
    fun isSuccess(): Boolean {
        return code == 200
    }
}

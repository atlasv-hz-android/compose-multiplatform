package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

    companion object {
        fun parse(text: String): UploadRecordData {
            return Json.decodeFromString(text)
        }
    }
}

package com.sample.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/10
 */

@Serializable
data class UploadResult(val code: Int, @SerialName("file_url") val fileUrl: String) {
    companion object {
        fun parse(text: String): UploadResult {
            return Json.decodeFromString(text)
        }
    }
}

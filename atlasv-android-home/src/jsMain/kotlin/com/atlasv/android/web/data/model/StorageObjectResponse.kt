package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/12
 */
@Serializable
data class StorageObjectResponse(val code: Int, val message: String, val items: List<StorageObject>) {

    companion object {
        fun parse(text: String): StorageObjectResponse {
            return Json.decodeFromString(text)
        }
    }
}

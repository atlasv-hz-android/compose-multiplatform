package com.atlasv.android.web.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/10
 */

@Serializable
data class UploadRecordItem(
    @SerialName("file_url") val fileUrl: String,
    @SerialName("content_type") val contentType: String,
    @SerialName("create_at") val createAt: String,
)

package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/12
 */
@Serializable
data class StorageObject(
    val bucket: String,
    val path: String,
    val size: Int
)

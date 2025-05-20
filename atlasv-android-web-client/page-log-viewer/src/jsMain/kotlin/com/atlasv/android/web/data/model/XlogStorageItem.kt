package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/5/20
 */
@Serializable
data class XlogStorageItem(val appPackage: String, val path: String)

package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/20
 */
@Serializable
data class AppPerfDataParent(
    val reports: List<AppPerfDataWrapper>
)

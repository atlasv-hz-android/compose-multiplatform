package com.atlasv.android.web.common.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class DateModel(
    val year: Int,
    val month: Int,
    val day: Int
) {
    fun getSortWeight(): Int {
        return year * 365 + month * 30 + day
    }
}

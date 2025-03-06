package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/3/6
 */
@Serializable
data class QueryRecordResponse(val data: List<QueryRecord>)

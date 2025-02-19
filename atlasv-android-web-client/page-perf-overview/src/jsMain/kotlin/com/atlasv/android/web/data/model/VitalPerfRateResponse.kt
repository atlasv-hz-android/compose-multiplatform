package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateResponse(val rows: List<VitalPerfRateModel>) {
    fun flatterByDimensions(): List<VitalPerfRateResponse> {
        val dimensions = this.rows.asSequence().map {
            it.dimensions.orEmpty()
        }.flatten().filter {
            it.int64Value < 3072 // 低端机，Ram<=3G。3072算进了3G-4G的范围，故这里用<
        }.distinctBy { it.int64Value }.sortedByDescending { it.int64Value }
            .toList()
        return dimensions.map { dimension ->
            this.copy(
                rows = rows.filter {
                    it.dimensions?.firstOrNull()?.int64Value == dimension.int64Value
                }
            ).sortedByDateDescending()
        }
    }

    fun sortedByDateDescending(): VitalPerfRateResponse {
        return this.copy(rows = this.rows.sortedByDescending {
            it.startTime.getSortWeight()
        })
    }
}
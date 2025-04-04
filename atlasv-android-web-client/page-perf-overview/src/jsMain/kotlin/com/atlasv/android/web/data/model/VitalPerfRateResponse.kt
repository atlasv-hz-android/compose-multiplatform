package com.atlasv.android.web.data.model

import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.model.TableRowModel
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/18
 */
@Serializable
data class VitalPerfRateResponse(val rows: List<VitalPerfRateModel>) {
    fun flattenByDimensions(): List<VitalPerfRateResponse> {
        val dayCount = rows.distinctBy { it.startTime }.size
        val allDimensions = this.rows.map {
            it.dimensions.orEmpty()
        }.flatten().filter {
            it.int64Value < 3072 // 低端机，Ram<=3G。3072算进了3G-4G的范围，故这里用<
        }
        val distinctDimensions = allDimensions.distinctBy {
            it.int64Value
        }
        val validDimensions = distinctDimensions.filter { distinctDimension ->
            val count = allDimensions.count {
                it == distinctDimension
            }
            count == dayCount
        }

        return validDimensions.map { dimension ->
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

    fun asTableRowModel(appName: String, perfType: String): TableRowModel {
        val cells = listOf(
            TableCellModel(
                text = appName, isHeaderCell = true
            ), TableCellModel(
                text = perfType
            ), TableCellModel(
                text = rows.firstOrNull().asDimensionCell()
            )
        ) + rows.map { it.asTableCellModel() }
        return TableRowModel(cells = cells)
    }
}


fun List<VitalPerfRateResponse>.mergeByDistinctUsers(dimensionLabel: String): VitalPerfRateResponse? {
    if (this.size <= 1) {
        return this.firstOrNull()
    }
    val mergedModels: List<VitalPerfRateModel> = this.first().rows.indices.mapNotNull { rowIndex ->
        val itemsIfRowIndex: List<VitalPerfRateModel> = this.map {
            it.rows[rowIndex]
        }
        val mergedModel = itemsIfRowIndex.mergeByDistinctUsers(dimensionLabel)
        mergedModel
    }
    return this[0].copy(
        rows = mergedModels
    )
}

fun List<VitalPerfRateModel>.mergeByDistinctUsers(dimensionLabel: String): VitalPerfRateModel? {
    if (this.size <= 1) {
        return this.firstOrNull()
    }
    val totalUsers = this.sumOf {
        it.getDistinctUsers()
    }
    val rate: Double = this.sumOf {
        val partRate = it.getFirstMetricRate() * (it.getDistinctUsers().toDouble() / totalUsers)
        partRate
    }
    val firstItem = this.first()
    val firstMetric = firstItem.metrics[0]
    val firstMetricValue = firstMetric.decimalValue
    val newFirstMetric = firstMetric.copy(
        decimalValue = firstMetricValue.copy(
            value = rate.toString()
        )
    )

    val firstDimension = firstItem.dimensions.orEmpty().first()
    return firstItem.copy(
        metrics = firstItem.metrics.toMutableList().apply {
            set(0, newFirstMetric)
        },
        dimensions = firstItem.dimensions?.toMutableList()?.apply {
            set(0, firstDimension.copy(valueLabel = dimensionLabel))
        }
    )
}
package com.atlasv.android.web.data.model

import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.model.TableRowModel

/**
 * Created by weiping on 2025/2/19
 */
data class AppPerfData(
    val appPackage: String,
    val appNickName: String,
    val anrNoDimensionData: VitalPerfRateResponse?,
    val anrDimensionData: VitalPerfRateResponse?,
    val crashNoDimensionData: VitalPerfRateResponse?,
    val crashDimensionData: VitalPerfRateResponse?,
) {
    val anrDimensionDataFlattened: List<VitalPerfRateResponse> = anrDimensionData?.flattenByDimensions().orEmpty()
    val lowRamAnrData: VitalPerfRateResponse? =
        anrDimensionDataFlattened.mergeByDistinctUsers(dimensionLabel = "低端机")

    val crashDimensionDataFlattened = crashDimensionData?.flattenByDimensions().orEmpty()
    val lowRamCrashData = crashDimensionDataFlattened.mergeByDistinctUsers(dimensionLabel = "低端机")

    fun getAnrData(simplifyMode: Boolean): List<VitalPerfRateResponse> {
        return if (!simplifyMode) {
            listOfNotNull(anrNoDimensionData) + anrDimensionDataFlattened
        } else {
            listOfNotNull(anrNoDimensionData) + listOfNotNull(
                lowRamAnrData
            )
        }
    }

    fun getCrashData(simplifyMode: Boolean): List<VitalPerfRateResponse> {
        return if (!simplifyMode) {
            listOfNotNull(crashNoDimensionData) + crashDimensionDataFlattened
        } else {
            listOfNotNull(crashNoDimensionData) + listOfNotNull(lowRamCrashData)
        }
    }

    fun createTableHeadRowModel(): TableRowModel? {
        return this.anrNoDimensionData?.rows?.let { rows ->
            val headerCells = listOf(
                TableCellModel(
                    text = "App", id = 0, isHeaderCell = true
                ), TableCellModel(
                    text = "指标", id = 0, isHeaderCell = true
                ), TableCellModel(
                    text = "设备维度", id = 0, isHeaderCell = true
                )
            ) + rows.map {
                it.asTableHeaderCellModel()
            }
            TableRowModel(headerCells)
        }
    }
}

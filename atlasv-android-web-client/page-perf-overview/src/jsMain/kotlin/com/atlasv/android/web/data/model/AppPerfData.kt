package com.atlasv.android.web.data.model

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
}

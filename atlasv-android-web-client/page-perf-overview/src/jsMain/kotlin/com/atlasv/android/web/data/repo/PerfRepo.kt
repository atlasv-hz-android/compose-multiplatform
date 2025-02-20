package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * Created by weiping on 2025/2/17
 */
class PerfRepo(private val httpEngine: HttpEngine) {
    private val totalDays = 8
    private val requestSemaphore by lazy {
        Semaphore(permits = 6)
    }

    suspend fun getAnr(appPackage: String, dimension: PerfDimensionType?): VitalPerfRateResponse? {
        val url = "${baseUrl}api/perf/get_user_perceived_anr_rate?app_package=$appPackage&total_days=$totalDays"
            .appendDimension(dimension)
        return requestSemaphore.withPermit {
            HttpEngine.json.decodeFromString(httpEngine.client.get(url).bodyAsText())
        }
    }

    suspend fun getCrash(appPackage: String, dimension: PerfDimensionType?): VitalPerfRateResponse? {
        val url = "${baseUrl}api/perf/get_user_perceived_crash_rate?app_package=$appPackage&total_days=$totalDays"
            .appendDimension(dimension)
        return requestSemaphore.withPermit {
            HttpEngine.json.decodeFromString(httpEngine.client.get(url).bodyAsText())
        }
    }

    private fun String.appendDimension(dimension: PerfDimensionType?): String {
        return this + if (dimension == null) "" else "&dimension=${dimension.value}"
    }

    suspend fun loadAppPerfData(appPackage: String): AppPerfData {
        return coroutineScope {
            val anrNoDimensionDataJob = async {
                getAnr(
                    appPackage = appPackage, dimension = null
                )?.sortedByDateDescending()
            }

            val anrDimensionDataJob = async {
                getAnr(
                    appPackage = appPackage,
                    dimension = PerfDimensionType.RamBucket
                )
            }

            val crashNoDimensionDataJob = async {
                getCrash(appPackage = appPackage, dimension = null)
                    ?.sortedByDateDescending()
            }

            val crashDimensionDataJob = async {
                getCrash(
                    appPackage = appPackage,
                    dimension = PerfDimensionType.RamBucket
                )
            }
            AppPerfData(
                appPackage = appPackage,
                anrNoDimensionData = anrNoDimensionDataJob.await(),
                anrDimensionData = anrDimensionDataJob.await(),
                crashNoDimensionData = crashNoDimensionDataJob.await(),
                crashDimensionData = crashDimensionDataJob.await()
            )
        }
    }

    companion object {
        val instance by lazy {
            PerfRepo(HttpEngine)
        }
    }
}
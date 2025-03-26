package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.AppPerfData
import com.atlasv.android.web.data.model.AppPerfDataParent
import com.atlasv.android.web.data.model.AppPerfDataWrapper
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/17
 */
class PerfRepo(private val httpEngine: HttpEngine) {
    suspend fun getAllVitalsData(): AppPerfDataParent {
        return httpEngine.json.decodeFromString(
            httpEngine.client.get("${HttpEngine.COMPUTE_ENGINE_URL}:20001/api/perf/get_all_app_vital_perf")
                .bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            PerfRepo(HttpEngine)
        }

        fun createPerfData(allVitalsData: AppPerfDataParent): List<AppPerfData> {
            val appPackageWithNickName = allVitalsData.reports.map {
                it.appPackage to it.appNickname
            }.distinctBy { (appPackage, _) -> appPackage }.sortedBy { (appPackage, _) ->
                AppEnum.entries.indexOfFirst {
                    it.packageName == appPackage
                }
            }
            return appPackageWithNickName.map { (appPackage, appNickName) ->
                val dataWrappers: List<AppPerfDataWrapper> = allVitalsData.reports.filter {
                    it.appPackage == appPackage
                }
                AppPerfData(
                    appPackage = appPackage,
                    appNickName = appNickName,
                    anrNoDimensionData = dataWrappers.find {
                        it.fileName.endsWith("userPerceivedAnrRate.json")
                    }?.data?.sortedByDateDescending(),
                    anrDimensionData = dataWrappers.find {
                        it.fileName.endsWith("userPerceivedAnrRate-deviceRamBucket.json")
                    }?.data?.sortedByDateDescending(),
                    crashNoDimensionData = dataWrappers.find {
                        it.fileName.endsWith("userPerceivedCrashRate.json")
                    }?.data?.sortedByDateDescending(),
                    crashDimensionData = dataWrappers.find {
                        it.fileName.endsWith("userPerceivedCrashRate-deviceRamBucket.json")
                    }?.data?.sortedByDateDescending()
                )
            }

        }
    }

}
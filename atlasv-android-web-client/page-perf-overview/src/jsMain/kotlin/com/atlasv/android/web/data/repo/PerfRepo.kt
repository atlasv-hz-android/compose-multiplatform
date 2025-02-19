package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/17
 */
class PerfRepo(private val httpEngine: HttpEngine) {
    suspend fun getAnr(appPackage: String, dimension: PerfDimensionType?): VitalPerfRateResponse? {
        val url = "${baseUrl}api/perf/get_user_perceived_anr_rate?app_package=$appPackage"
            .appendDimension(dimension)
        return HttpEngine.json.decodeFromString(httpEngine.client.get(url).bodyAsText())
    }

    suspend fun getCrash(appPackage: String, dimension: PerfDimensionType?): VitalPerfRateResponse? {
        val url = "${baseUrl}api/perf/get_user_perceived_crash_rate?app_package=$appPackage"
            .appendDimension(dimension)
        return HttpEngine.json.decodeFromString(
            httpEngine.client.get(url)
                .bodyAsText()
        )
    }

    private fun String.appendDimension(dimension: PerfDimensionType?): String {
        return this + if (dimension == null) "" else "&dimension=${dimension.value}"
    }

    companion object {
        val instance by lazy {
            PerfRepo(HttpEngine)
        }
    }
}
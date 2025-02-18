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
    suspend fun get(): VitalPerfRateResponse? {
        return HttpEngine.json.decodeFromString(
            httpEngine.client.get("${baseUrl}perf/get_user_perceived_anr_rate").bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            PerfRepo(HttpEngine)
        }
    }
}
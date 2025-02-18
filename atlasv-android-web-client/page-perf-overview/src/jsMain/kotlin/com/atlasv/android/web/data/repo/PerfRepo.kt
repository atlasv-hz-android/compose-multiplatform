package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.VitalPerfRateResponse
import com.atlasv.android.web.data.model.VitalPerfRateResponseGroup
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/17
 */
class PerfRepo(private val httpEngine: HttpEngine) {
    suspend fun get(): VitalPerfRateResponseGroup? {
        return HttpEngine.json.decodeFromString(
            httpEngine.client.get("${baseUrl}api/perf/get_user_perceived_vital_rate").bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            PerfRepo(HttpEngine)
        }
    }
}
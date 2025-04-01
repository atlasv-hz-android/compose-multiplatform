package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.COMPUTE_ENGINE_URL
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.UploadRecordData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/11
 */
class FileUploadRepository(private val client: HttpClient) {
    suspend fun queryHistory(): UploadRecordData {
        return HttpEngine.json.decodeFromString(client.get("${HttpEngine.computeEngineUrlIp}/api/upload/history").bodyAsText())
    }

    companion object {
        val instance by lazy {
            FileUploadRepository(HttpEngine.client)
        }
    }
}
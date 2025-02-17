package com.atlasv.android.web.data.repo

import com.atlasv.android.web.core.network.HttpEngine
import com.atlasv.android.web.core.network.HttpEngine.baseUrl
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
        return Json.decodeFromString(client.get("${baseUrl}upload_history").bodyAsText())
    }

    companion object {
        val instance by lazy {
            FileUploadRepository(HttpEngine.client)
        }
    }
}
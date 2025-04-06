package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.BucketType
import com.atlasv.android.web.data.model.UploadRecordData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/11
 */
class FileUploadRepository(private val client: HttpClient) {
    suspend fun queryHistory(type: BucketType?): UploadRecordData? {
        type ?: return null
        return HttpEngine.json.decodeFromString(
            client.get("${HttpEngine.computeEngineUrlIp}/api/upload/history?bucket_type=${type.name}").bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            FileUploadRepository(HttpEngine.client)
        }
    }
}
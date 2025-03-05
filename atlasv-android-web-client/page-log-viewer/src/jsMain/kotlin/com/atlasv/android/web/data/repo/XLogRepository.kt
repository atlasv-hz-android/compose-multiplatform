package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.StorageObjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/12
 */
class XLogRepository(private val client: HttpClient) {

    suspend fun queryLogs(uid: String, appPackage: String): StorageObjectResponse? {
        if (uid.isEmpty()) {
            return null
        }
        return Json.decodeFromString(client.get("${baseUrl}list_logs?uid=$uid&app_package=${appPackage}").bodyAsText())
    }

    companion object {
        val instance by lazy {
            XLogRepository(HttpEngine.client)
        }
    }
}
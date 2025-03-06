package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.Constants
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.QueryRecord
import com.atlasv.android.web.data.model.QueryRecordResponse
import com.atlasv.android.web.data.model.StorageObjectResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/12
 */
class XLogRepository(private val httpEngine: HttpEngine) {
    private val client by lazy {
        httpEngine.client
    }

    suspend fun queryLogs(uid: String, appPackage: String?): StorageObjectResponse? {
        appPackage ?: return null
        if (uid.isEmpty()) {
            return null
        }
        return httpEngine.json.decodeFromString<StorageObjectResponse?>(
            client.get("${baseUrl}list_logs?uid=$uid&app_package=${appPackage}").bodyAsText()
        )?.copy(
            appPackage = appPackage
        )
    }

    suspend fun queryHistoryUidList(): QueryRecordResponse? {
        if (Constants.DEBUG) {
            return QueryRecordResponse(
                data = (1..20).map {
                    QueryRecord(
                        uid = "626082852982894592",
                        appPackage = AppEnum.Ins3.packageName
                    )
                }
            )
        }
        return httpEngine.json.decodeFromString(
            client.get("${baseUrl}api/xlog/history_uids").bodyAsText()
        )
    }


    companion object {
        val instance by lazy {
            XLogRepository(HttpEngine)
        }
    }
}
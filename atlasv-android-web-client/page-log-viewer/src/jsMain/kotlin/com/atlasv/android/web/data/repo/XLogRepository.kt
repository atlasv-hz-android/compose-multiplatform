package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.QueryRecordResponse
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.model.VipInfo
import com.atlasv.android.web.data.model.VipUserInfo
import com.atlasv.android.web.data.model.XlogStorageItem
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.encodeURLQueryComponent

/**
 * Created by weiping on 2025/2/12
 */
class XLogRepository(private val httpEngine: HttpEngine, private val baseUrl: String) {
    private val client by lazy {
        httpEngine.client
    }

    suspend fun queryLogs(uid: String, appPackage: String?): StorageObjectResponse? {
        appPackage ?: return null
        if (uid.isEmpty()) {
            return null
        }
        return httpEngine.json.decodeFromString<StorageObjectResponse?>(
            client.get("${baseUrl}/xlog/api/list_logs?uid=$uid&app_package=${appPackage}")
                .bodyAsText()
        )?.copy(
            appPackage = appPackage
        )
    }

    suspend fun queryHistoryUidList(): QueryRecordResponse? {
        return httpEngine.json.decodeFromString(
            client.get("${baseUrl}/xlog/api/history_uids").bodyAsText()
        )
    }

    suspend fun queryVipInfo(userId: String): VipInfo? {
        return httpEngine.json.decodeFromString(
            client.get("${baseUrl}/xlog/api/query_vip_info?user_id=$userId").bodyAsText()
        )
    }

    suspend fun queryVipUserInfo(orderId: String): VipUserInfo? {
        return httpEngine.json.decodeFromString(
            client.get("${baseUrl}/xlog/api/query_vip_user_info?order_id=$orderId").bodyAsText()
        )
    }

    fun buildDownloadUrl(storageItem: XlogStorageItem, asAttachment: Boolean): String {
        val downloadBaseUrl = "${baseUrl}/xlog/api/download_xlog"
        return "$downloadBaseUrl?blob_name=${storageItem.path.encodeURLQueryComponent()}&app_package=${storageItem.appPackage}&download=${if (asAttachment) "1" else "0"}"
    }

    companion object {
        fun create(windowHrefUrl: String): XLogRepository {
            return XLogRepository(HttpEngine, HttpEngine.createApiBaseUrlByWindowHref(windowHrefUrl))
        }
    }
}
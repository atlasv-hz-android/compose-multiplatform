package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.DiskReportDetail
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/26
 */
class DiskReportRepo(private val httpEngine: HttpEngine) {

    /**
     * [列出磁盘报告](http://34.28.167.123/api/perf/list_disk_report_files?app_package=facebook.video.downloader.savefrom.fb.saver.fast&priory=P1)
     */
    suspend fun listReportFiles(
        appPackage: String?,
        priority: Int
    ): DiskReportDetail? {
        appPackage ?: return null
        return httpEngine.json.decodeFromString(
            httpEngine.client.get(
                "${HttpEngine.COMPUTE_ENGINE_URL}/api/perf/list_disk_report_files?" +
                        "app_package=$appPackage" +
                        "&priority=P${priority}"
            ).bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            DiskReportRepo(HttpEngine)
        }
    }
}
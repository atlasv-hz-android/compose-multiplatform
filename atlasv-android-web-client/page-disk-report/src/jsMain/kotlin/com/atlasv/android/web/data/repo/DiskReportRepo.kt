package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.Constants
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.DiskReport
import com.atlasv.android.web.data.model.DiskReportDetail
import com.atlasv.android.web.data.model.DiskReportResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/26
 */
class DiskReportRepo(private val httpEngine: HttpEngine) {
    suspend fun getReports(): DiskReportResponse? {
        return httpEngine.json.decodeFromString(
            httpEngine.client.get("${baseUrl}api/perf/get_disk_reports?debug=${if (Constants.DEBUG) "1" else "0"}")
                .bodyAsText()
        )
    }

    suspend fun listReportFiles(
        report: DiskReport
    ): DiskReportDetail? {
        return listReportFiles(
            appPackage = report.appPackage,
            versionCode = report.versionCode,
            versionName = report.versionName,
            sizeG = report.sizeG
        )
    }

    private suspend fun listReportFiles(
        appPackage: String,
        versionCode: Int,
        versionName: String,
        sizeG: Int
    ): DiskReportDetail? {
        return httpEngine.json.decodeFromString(
            httpEngine.client.get(
                "${baseUrl}api/perf/list_disk_report_files?" +
                        "app_package=$appPackage" +
                        "&version_name=${versionName}" +
                        "&version_code=${versionCode}" +
                        "&size_g=${sizeG}"
            ).bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            DiskReportRepo(HttpEngine)
        }
    }
}
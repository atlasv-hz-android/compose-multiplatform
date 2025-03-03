package com.atlasv.android.web.ui.content

import com.atlasv.android.web.common.HttpEngine

/**
 * Created by weiping on 2025/2/28
 */
data class HomeFunctionModuleGroup(val title: String, val modules: List<HomeFunctionModule>) {
    companion object {
        val homeModuleGroups by lazy {
            listOf(
                HomeFunctionModuleGroup(
                    title = "应用性能监控", modules = listOf(
                        HomeFunctionModule(
                            title = "性能监控",
                            desc = "Android性能指标，ANR、Crash数据",
                            targetUrl = "${HttpEngine.baseUrl}perf-overview"
                        ),
                        HomeFunctionModule(
                            title = "磁盘报告",
                            desc = "查看用户磁盘占用情况",
                            targetUrl = "${HttpEngine.baseUrl}disk-report"
                        )
                    )
                ),
                HomeFunctionModuleGroup(
                    title = "研发工具", modules = listOf(
                        HomeFunctionModule(
                            title = "日志查看器",
                            desc = "查看用户上传的日志",
                            targetUrl = "${HttpEngine.baseUrl}log_viewer_page"
                        )
                    )
                ),
                HomeFunctionModuleGroup(
                    title = "通用工具", modules = listOf(
                        HomeFunctionModule(
                            title = "文件上传",
                            desc = "通用的文件上传",
                            targetUrl = "${HttpEngine.baseUrl}upload_file_page"
                        )
                    )
                )
            )
        }
    }
}

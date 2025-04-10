package com.atlasv.android.web.ui.content

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.COMPUTE_ENGINE_URL

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
                            targetUrl = "$COMPUTE_ENGINE_URL/perf-overview"
                        ),
                        HomeFunctionModule(
                            title = "磁盘报告",
                            desc = "查看用户磁盘占用情况",
                            targetUrl = "${COMPUTE_ENGINE_URL}/disk-report"
                        )
                    )
                ),
                HomeFunctionModuleGroup(
                    title = "研发工具", modules = listOf(
                        HomeFunctionModule(
                            title = "SonarQube",
                            desc = "分析和测量代码质量，提升安全性、可靠性和可维护性",
                            targetUrl = "https://android-team-sonar.etm.tech/projects"
                        ), HomeFunctionModule(
                            title = "日志查看器",
                            desc = "查看用户上传的日志",
                            targetUrl = "${HttpEngine.computeEngineUrl}/log_viewer_page"
                        ), HomeFunctionModule(
                            title = "商品管理",
                            desc = "查看应用内订阅项、商品项",
                            targetUrl = "${HttpEngine.baseUrl}purchase_products_page"
                        )
                    )
                ),
                HomeFunctionModuleGroup(
                    title = "通用工具", modules = listOf(
                        HomeFunctionModule(
                            title = "文件上传(新)",
                            desc = "通用的文件上传",
                            targetUrl = "${HttpEngine.COMPUTE_ENGINE_URL_IP}/upload_file_page"
                        ),
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

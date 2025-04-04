package com.atlasv.android.web.common

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/10
 */
object HttpEngine {
    val client = HttpClient()
    val json by lazy {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
    const val COMPUTE_ENGINE_URL = "https://android-team-platform.etm.tech"
    const val COMPUTE_ENGINE_URL_IP = "http://34.28.167.123:20001"
    const val LOCAL_HOST = "http://127.0.0.1:20001"
    val computeEngineUrlIp = if (Constants.DEBUG) LOCAL_HOST else COMPUTE_ENGINE_URL_IP
    val computeEngineUrl = if (Constants.DEBUG) LOCAL_HOST else COMPUTE_ENGINE_URL
    val baseUrl = if (Constants.DEBUG) "http://127.0.0.1:8080/" else "https://atlasv-android-team.uc.r.appspot.com/"

}
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
    val baseUrl = if (Constants.DEBUG) "http://127.0.0.1:8080/" else "https://atlasv-android-team.uc.r.appspot.com/"

}
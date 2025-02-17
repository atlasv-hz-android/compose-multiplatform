package com.atlasv.android.web.common

import io.ktor.client.HttpClient

/**
 * Created by weiping on 2025/2/10
 */
object HttpEngine {
    val client = HttpClient()
    val baseUrl = if (Constants.DEBUG) "http://127.0.0.1:8080/" else "https://atlasv-android-team.uc.r.appspot.com/"
}
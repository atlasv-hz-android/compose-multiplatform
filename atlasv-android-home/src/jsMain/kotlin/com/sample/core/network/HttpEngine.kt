package com.sample.core.network

import com.sample.constant.Constants
import io.ktor.client.HttpClient

/**
 * Created by weiping on 2025/2/10
 */
object HttpEngine {
    private val client = HttpClient()
    val baseUrl = if (Constants.DEBUG) "http://127.0.0.1:8080/" else "https://fx-editor.ue.r.appspot.com/"
    val fileUploader by lazy {
        FileUploader(client = client)
    }
}
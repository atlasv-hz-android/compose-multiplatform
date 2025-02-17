package com.atlasv.android.web.data.repo

import com.atlasv.android.web.core.network.HttpEngine
import com.atlasv.android.web.core.network.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

/**
 * Created by weiping on 2025/2/12
 */
class ProductRepository(private val client: HttpClient) {

    suspend fun queryProducts(): ProductResponse {
        return Json.decodeFromString(client.get("${baseUrl}get_google_products").bodyAsText())
    }

    companion object {
        val instance by lazy {
            ProductRepository(HttpEngine.client)
        }
    }
}
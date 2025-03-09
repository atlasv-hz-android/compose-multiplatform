package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.data.model.ProductResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/12
 */
class ProductRepository(private val httpEngine: HttpEngine) {

    suspend fun queryProducts(appPackage:String): ProductResponse {
        return httpEngine.json.decodeFromString(httpEngine.client.get("${baseUrl}api/purchase/get_products?app_package=$appPackage").bodyAsText())
    }

    companion object {
        val instance by lazy {
            ProductRepository(HttpEngine)
        }
    }
}
package com.atlasv.android.web.data.repo

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.ProductOperationRecordResponse
import com.atlasv.android.web.data.model.ProductResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/2/12
 */
class ProductRepository(private val httpEngine: HttpEngine) {

    suspend fun queryProducts(appPackage: String): ProductResponse {
        return httpEngine.json.decodeFromString(
            httpEngine.client.get("${HttpEngine.computeEngineUrl}/api/purchase/get_products?app_package=$appPackage")
                .bodyAsText()
        )
    }

    suspend fun getProductOperationRecords(appPackage: String): ProductOperationRecordResponse {
        return httpEngine.json.decodeFromString(
            httpEngine.client.get("${HttpEngine.computeEngineUrl}/api/purchase/get_product_operation_records?app_package=$appPackage")
                .bodyAsText()
        )
    }

    suspend fun addProduct(
        appPackage: String,
        productId: String,
        entitlementId: String
    ): String {
        return httpEngine.client.get("${HttpEngine.computeEngineUrl}/api/purchase/add_product?app_package=$appPackage&product_id=$productId&entitlement_id=$entitlementId")
            .bodyAsText()
    }

    suspend fun deleteProduct(
        appPackage: String,
        productId: String,
    ): String {
        return httpEngine.client.get("${HttpEngine.computeEngineUrl}/api/purchase/delete_product?app_package=$appPackage&product_id=$productId")
            .bodyAsText()
    }

    companion object {
        val instance by lazy {
            ProductRepository(HttpEngine)
        }
    }
}
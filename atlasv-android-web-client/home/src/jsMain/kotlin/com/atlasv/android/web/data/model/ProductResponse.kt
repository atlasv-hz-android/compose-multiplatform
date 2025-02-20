package com.atlasv.android.web.data.model

import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/13
 */
@Serializable
data class ProductResponse(val products: List<ProductEntity>)

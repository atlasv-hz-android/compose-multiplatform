package com.atlasv.android.web.common.data.repo

import com.atlasv.android.web.common.Constants
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.baseUrl
import com.atlasv.android.web.common.data.model.IapUser
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/3/12
 */
class IapUserRepository(private val httpEngine: HttpEngine) {
    private val client by lazy {
        httpEngine.client
    }

    suspend fun getUser(): IapUser? {
        return httpEngine.json.decodeFromString<IapUser?>(
            client.get("${baseUrl}api/user/info?debug=${if (Constants.DEBUG) 1 else 0}").bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            IapUserRepository(HttpEngine)
        }
    }
}
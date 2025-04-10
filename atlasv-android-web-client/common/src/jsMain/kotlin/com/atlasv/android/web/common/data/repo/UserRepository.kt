package com.atlasv.android.web.common.data.repo

import com.atlasv.android.web.common.Constants
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.data.model.User
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

/**
 * Created by weiping on 2025/3/12
 */
class UserRepository(private val httpEngine: HttpEngine) {
    private val client by lazy {
        httpEngine.client
    }

    suspend fun getUser(): User? {
        return httpEngine.json.decodeFromString<User?>(
            client.get("${HttpEngine.computeEngineUrl}/api/user/info").bodyAsText()
        )
    }

    companion object {
        val instance by lazy {
            UserRepository(HttpEngine)
        }
    }
}
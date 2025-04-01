package com.atlasv.android.web.core.network

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.HttpEngine.COMPUTE_ENGINE_URL_IP
import com.atlasv.android.web.core.util.asMultiPartFormDataContent
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.w3c.files.File

/**
 * Created by weiping on 2025/2/10
 */
class FileUploader(private val client: HttpClient) {
    private val semaphore by lazy {
        Semaphore(permits = 4)
    }

    suspend fun upload(files: List<File>) {
        coroutineScope {
            files.map {
                async {
                    semaphore.withPermit {
                        upload(it)
                    }
                }
            }.awaitAll()
        }
    }

    private suspend fun upload(file: File) {
        // https://ktor.io/docs/client-requests.html#upload_file
        client.post("${COMPUTE_ENGINE_URL_IP}/upload_file") {
            setBody(file.asMultiPartFormDataContent())
        }
    }

    companion object {
        val instance by lazy {
            FileUploader(client = HttpEngine.client)
        }
    }
}
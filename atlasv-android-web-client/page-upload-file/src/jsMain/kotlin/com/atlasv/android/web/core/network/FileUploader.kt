package com.atlasv.android.web.core.network

import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.core.util.asMultiPartFormDataContent
import com.atlasv.android.web.data.model.BucketType
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

    suspend fun upload(files: List<File>, bucketType: BucketType?) {
        bucketType ?: return
        coroutineScope {
            files.map {
                async {
                    semaphore.withPermit {
                        upload(it, bucketType)
                    }
                }
            }.awaitAll()
        }
    }

    private suspend fun upload(file: File, bucketType: BucketType) {
        // https://ktor.io/docs/client-requests.html#upload_file
        client.post("${HttpEngine.computeEngineUrlIp}/upload_file_to_oss") {
            setBody(
                file.asMultiPartFormDataContent(
                    formMap = mapOf(
                        "bucket_type" to bucketType.name,
                        "bucket_name" to bucketType.bucketName,
                        "region_name" to bucketType.regionName,
                        "path" to bucketType.path,
                    )
                )
            )
        }
    }

    companion object {
        val instance by lazy {
            FileUploader(client = HttpEngine.client)
        }
    }
}
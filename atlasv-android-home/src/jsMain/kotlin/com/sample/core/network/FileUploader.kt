package com.sample.core.network

import com.sample.core.network.HttpEngine.baseUrl
import com.sample.core.util.asMultiPartFormDataContent
import com.sample.data.model.UploadResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.w3c.files.File

/**
 * Created by weiping on 2025/2/10
 */
class FileUploader(private val client: HttpClient) {
    suspend fun upload(file: File): UploadResult {
        // https://ktor.io/docs/client-requests.html#upload_file
        val response: HttpResponse = client.post("${baseUrl}upload_file") {
            setBody(file.asMultiPartFormDataContent())
        }
        return UploadResult.parse(response.bodyAsText())
    }
}
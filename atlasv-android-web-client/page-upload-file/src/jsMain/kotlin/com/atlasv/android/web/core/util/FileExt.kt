package com.atlasv.android.web.core.util

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.khronos.webgl.ArrayBuffer
import org.w3c.files.File

/**
 * Created by weiping on 2025/2/10
 */

suspend fun File.asMultiPartFormDataContent(formMap: Map<String, String>): MultiPartFormDataContent {
    val file = this
    val buffer: ArrayBuffer = file.buffer()
    val byteArray = buffer.toByteArray()
    return MultiPartFormDataContent(
        formData {
            formMap.entries.forEach {
                append(it.key, it.value)
            }
            append("file", byteArray, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
            })
        }
    )
}
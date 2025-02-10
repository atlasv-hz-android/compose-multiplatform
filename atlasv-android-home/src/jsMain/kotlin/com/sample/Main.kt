package com.sample

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.sample.components.Layout
import com.sample.style.AppStylesheet
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.dom.asList
import org.w3c.files.Blob
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Serializable
data class Data(val name: String, val age: Int)

val client = HttpClient()
val debug = false
val baseUrl = if (debug) "http://127.0.0.1:8080/" else "https://fx-editor.ue.r.appspot.com/"

suspend fun fetchData(url: String): Data {
    val response = window.fetch(url).await()
    val text = response.text().await()
    console.log(text)
    return Json.decodeFromString(text)
}

fun main() {
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        val scope = rememberCoroutineScope()
        var text by remember { mutableStateOf<String?>(null) }
        val data = remember { mutableStateOf<Data?>(null) }

        Layout {
            Input(
                type = InputType.File,
                attrs = {
                    onChange {
                        val file: File = it.target.files!!.asList().single()
                        scope.launch {
                            val buffer: ArrayBuffer = file.buffer()
                            val byteArray = buffer.toByteArray()
                            // https://ktor.io/docs/client-requests.html#upload_file
                            val response: HttpResponse = client.post("${baseUrl}upload_file") {
                                setBody(
                                    MultiPartFormDataContent(
                                        formData {
                                            append("file", byteArray, Headers.build {
                                                append(HttpHeaders.ContentType, "image/webp")
                                                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                                            })
                                        },
                                        boundary = "WebAppBoundary"
                                    )
                                )
                                onUpload { bytesSentTotal, contentLength ->
                                    println("Sent $bytesSentTotal bytes from $contentLength")
                                }
                            }
                        }
                    }
                }
            )
            Text("text=${data.value}")
        }

        LaunchedEffect(Unit) {
            data.value = fetchData("${baseUrl}get_item")
        }
    }
}

suspend fun Blob.buffer(): ArrayBuffer = suspendCoroutine {
    FileReader().apply {
        onload = { _ ->
            it.resume(result as ArrayBuffer)
        }
        onerror = { _ ->
            it.resumeWithException(Error("reading file: $this"))
        }
    }.readAsArrayBuffer(this)
}

fun ArrayBuffer.toByteArray(): ByteArray = Int8Array(buffer = this).unsafeCast<ByteArray>()



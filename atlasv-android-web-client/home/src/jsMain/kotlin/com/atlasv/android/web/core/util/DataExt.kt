package com.atlasv.android.web.core.util

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.files.Blob
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by weiping on 2025/2/10
 */
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

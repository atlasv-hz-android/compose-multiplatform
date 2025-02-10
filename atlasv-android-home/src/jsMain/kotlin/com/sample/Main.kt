package com.sample

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sample.components.Layout
import com.sample.core.network.HttpEngine
import com.sample.data.model.UploadResult
import com.sample.style.AppStylesheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.asList
import org.w3c.files.File
fun main() {
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        val data = remember { mutableStateOf<UploadResult?>(null) }
        Layout {
            Input(
                type = InputType.File,
                attrs = {
                    onChange {
                        val file: File = it.target.files!!.asList().single()
                        CoroutineScope(Dispatchers.Default).launch {
                            data.value = HttpEngine.fileUploader.upload(file)
                        }
                    }
                }
            )
            Text("text=${data.value}")
        }
    }
}


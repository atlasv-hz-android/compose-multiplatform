package com.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sample.core.network.HttpEngine
import com.sample.data.model.UploadResult
import com.sample.style.CommonStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.events.SyntheticChangeEvent
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File

fun main() {
    renderComposable(rootElementId = "root") {
        val uploadResult = remember { mutableStateOf<UploadResult?>(null) }
        Body(
            onFileInputChange = {
                val file: File = it.target.files!!.asList().single()
                CoroutineScope(Dispatchers.Default).launch {
                    uploadResult.value = HttpEngine.fileUploader.upload(file)
                }
            }
        )
    }
}

@Composable
fun Body(onFileInputChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit) {
    Style(CommonStyles)
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            Div(
                {
                    classes(CommonStyles.p33)
                },
                content = {
                    Div(
                        attrs = {
                            classes(CommonStyles.matCard)
                        },
                        content = {
                            FileUploadInput(onChange = onFileInputChange)
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun FileUploadInput(onChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit) {
    Input(
        type = InputType.File,
        attrs = {
            onChange { it: SyntheticChangeEvent<String, HTMLInputElement> ->
                onChange(it)
            }
        })
}

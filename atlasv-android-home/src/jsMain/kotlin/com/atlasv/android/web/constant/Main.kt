package com.atlasv.android.web.constant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.core.network.HttpEngine
import com.atlasv.android.web.data.model.UploadResult
import com.atlasv.android.web.style.CommonStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.events.SyntheticChangeEvent
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var uploadResult by remember { mutableStateOf<UploadResult?>(null) }
    val onFileInputChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit = {
        val file: File = it.target.files!!.asList().single()
        CoroutineScope(Dispatchers.Default).launch {
            uploadResult = HttpEngine.fileUploader.upload(file)
        }
    }
    Style(CommonStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
        },
        content = {
            FunctionCards(onFileInputChange)
            UploadResultView(uploadResult)
        }
    )
}

@Composable
private fun UploadResultView(uploadResult: UploadResult?) {
    if (uploadResult == null) {
        return
    }
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
            style {
                paddingTop(12.px)
                paddingBottom(12.px)
                justifyContent(JustifyContent.Center)
            }
        },
        content = {
            if (uploadResult.isSuccess()) {
                A(
                    attrs = {
                        target(ATarget.Blank)
                    },
                    href = uploadResult.fileUrl
                ) {
                    Text(uploadResult.fileUrl)
                }
            } else {
                Text("上传失败：${uploadResult.code}: ${uploadResult.message}")
            }
        }
    )
}

@Composable
private fun FunctionCards(onFileInputChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit) {
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

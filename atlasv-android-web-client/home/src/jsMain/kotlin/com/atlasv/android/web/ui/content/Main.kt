package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.core.network.HttpEngine
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.model.UploadRecordData
import com.atlasv.android.web.data.repo.FileUploadRepository
import com.atlasv.android.web.data.repo.ProductRepository
import com.atlasv.android.web.data.repo.XLogRepository
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
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
    var uploadRecordData by remember { mutableStateOf<UploadRecordData?>(null) }
    var xLogResponse by remember { mutableStateOf<StorageObjectResponse?>(null) }
    var productResponse by remember { mutableStateOf<ProductResponse?>(null) }
    var loading by remember { mutableStateOf(false) }
    val onFileInputChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit = {
        val file: File? = it.target.files?.asList()?.singleOrNull()
        if (!loading && file != null) {
            CoroutineScope(Dispatchers.Default).launch {
                loading = true
                HttpEngine.fileUploader.upload(file)
                loading = false
                launch {
                    uploadRecordData = FileUploadRepository.instance.queryHistory()
                }
            }
        }
    }
    Style(CommonStyles)
    Style(TextStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                paddingLeft(16.px)
                paddingRight(16.px)
            }
        },
        content = {
            FunctionCards(onFileInputChange, loading)
            UploadHistory(uploadRecordData)
            XLogListView(xLogResponse, onClick = {

            })
            ProductListView(productResponse, onClick = {

            })
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            uploadRecordData = FileUploadRepository.instance.queryHistory()
            launch {
                xLogResponse = XLogRepository.instance.queryLogs()
            }
            launch {
                productResponse = ProductRepository.instance.queryProducts()
            }
        }
    }
}

@Composable
private fun FunctionCards(
    onFileInputChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit,
    loading: Boolean
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            Div(
                attrs = {
                    classes(CommonStyles.p33)
                },
                content = {
                    Div(
                        attrs = {
                            classes(CommonStyles.matCard)
                        },
                        content = {
                            FileUploadInput(onChange = onFileInputChange, loading)
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun FileUploadInput(onChange: (SyntheticChangeEvent<String, HTMLInputElement>) -> Unit, loading: Boolean) {
    if (!loading) {
        Input(
            type = InputType.File,
            attrs = {
                onChange { it: SyntheticChangeEvent<String, HTMLInputElement> ->
                    onChange(it)
                }
            })
    } else {
        Text("上传中，请稍候...")
    }
}

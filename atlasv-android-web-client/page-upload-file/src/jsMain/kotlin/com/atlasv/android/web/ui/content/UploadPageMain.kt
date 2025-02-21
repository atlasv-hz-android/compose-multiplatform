package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.core.network.FileUploader
import com.atlasv.android.web.data.model.UploadRecordData
import com.atlasv.android.web.data.repo.FileUploadRepository
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.multiple
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.asList
import org.w3c.files.File
import org.w3c.files.FileList

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var uploadRecordData by remember { mutableStateOf<UploadRecordData?>(null) }
    var loading by remember { mutableStateOf(false) }
    val onFileInputChange: (FileList?) -> Unit = {
        val files: List<File> = it?.asList().orEmpty()
        if (!loading && files.isNotEmpty()) {
            CoroutineScope(Dispatchers.Default).launch {
                loading = true
                FileUploader.instance.upload(files)
                loading = false
                uploadRecordData = FileUploadRepository.instance.queryHistory()
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
            VerticalDivider(height = 24.px)
            FunctionCards(onFileInputChange, loading)
            UploadHistory(uploadRecordData)
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            uploadRecordData = FileUploadRepository.instance.queryHistory()
        }
    }
}

@Composable
private fun FunctionCards(
    onFilesSelected: (FileList?) -> Unit,
    loading: Boolean
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter)
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
                            FileUploadInput(onFilesSelected = onFilesSelected, loading)
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun FileUploadInput(onFilesSelected: (FileList?) -> Unit, loading: Boolean) {
    if (!loading) {
        Input(
            type = InputType.File,
            attrs = {
                onInput {
                    onFilesSelected(it.target.files)
                }
                multiple()
            })
    } else {
        Text("上传中，请稍候...")
    }
}

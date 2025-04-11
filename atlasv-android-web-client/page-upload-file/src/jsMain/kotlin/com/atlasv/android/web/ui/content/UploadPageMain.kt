package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.core.network.FileUploader
import com.atlasv.android.web.data.model.BucketType
import com.atlasv.android.web.data.model.UploadRecordData
import com.atlasv.android.web.data.repo.FileUploadRepository
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.tab.TabContainer
import com.atlasv.android.web.ui.model.TabModel
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TabStyle
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
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLDivElement
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
    var _uploadRecordData by remember { mutableStateOf<Pair<BucketType, UploadRecordData>?>(null) }
    var loading by remember { mutableStateOf(false) }
    val tabModels = BucketType.entries.map {
        TabModel(
            text = it.displayName,
            id = it.ordinal
        )
    }
    var currentUploadType by remember {
        mutableStateOf<BucketType?>(null)
    }
    val uploadRecordData = _uploadRecordData?.takeIf { it.first == currentUploadType }?.second
    val onFileInputChange: (FileList?) -> Unit = {
        val files: List<File> = it?.asList().orEmpty()
        if (!loading && files.isNotEmpty()) {
            CoroutineScope(Dispatchers.Default).launch {
                console.log("currentUploadType=$currentUploadType")
                loading = true
                FileUploader.instance.upload(files, currentUploadType)
                loading = false
                _uploadRecordData = FileUploadRepository.instance.queryHistory(currentUploadType)
            }
        }
    }
    Style(CommonStyles)
    Style(TextStyles)
    Style(TabStyle)
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
            style {
                paddingLeft(16.px)
                paddingRight(16.px)
            }
        },
        content = {
            VerticalDivider(height = 24.px)
            UploadBucketTypes(
                models = tabModels, onTabClick = { tabModel ->
                    currentUploadType = BucketType.entries.find {
                        it.ordinal == tabModel.id
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        _uploadRecordData = FileUploadRepository.instance.queryHistory(currentUploadType)
                    }
                },
                tabContentBuilder = {
                    if (currentUploadType != null) {
                        FunctionCards(onFileInputChange, loading)
                        UploadHistory(uploadRecordData)
                    } else {
                        ErrorTip("先选择使用场景")
                    }
                }
            )
        }
    )
}

@Composable
private fun ErrorTip(text: String) {
    Div(
        attrs = {
            classes(TextStyles.errorTip)
        },
        content = {
            Text(text)
        }
    )
}

@Composable
private fun UploadBucketTypes(
    models: List<TabModel>,
    onTabClick: (TabModel) -> Unit,
    tabContentBuilder: ContentBuilder<HTMLDivElement>,
) {
    TabContainer(
        models = models,
        onTabClick = onTabClick,
        tabContentBuilder = tabContentBuilder,
        initIndex = -1
    )
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

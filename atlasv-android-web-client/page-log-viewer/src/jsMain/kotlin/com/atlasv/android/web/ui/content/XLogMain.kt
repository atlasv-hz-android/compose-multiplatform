package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.repo.XLogRepository
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.PrimaryButton
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var xLogResponse by remember { mutableStateOf<StorageObjectResponse?>(null) }
    var loading by remember { mutableStateOf(false) }
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    Style(CommonStyles)
    Style(TextStyles)
    val apps = listOf(AppEnum.Ins3)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                paddingLeft(16.px)
                paddingRight(16.px)
            }
        },
        content = {
            VerticalDivider(height = 20.px)
            AppTabLayout(
                items = apps.map {
                    TabItemData(text = it.name, id = it.ordinal)
                },
                onItemClick = { id ->
                    if (id != currentApp?.ordinal) {
                        currentApp = apps.find {
                            it.ordinal == id
                        }
                        xLogResponse = null
                    }
                },
                selectedIndex = currentApp?.let {
                    apps.indexOf(it)
                } ?: -1
            )
            VerticalDivider(height = 16.px)
            TextInputView(
                onClickSearch = { content ->
                    CoroutineScope(Dispatchers.Default).launch {
                        loading = true
                        xLogResponse = XLogRepository.instance.queryLogs(uid = content, currentApp?.packageName)
                        loading = false
                    }
                },
                loading = loading,
                enable = !loading && currentApp != null
            )
            XLogListView(xLogResponse, onClick = {

            })
        }
    )
}

@Composable
private fun TextInputView(onClickSearch: (String) -> Unit, loading: Boolean, enable: Boolean) {
    var content by remember {
        mutableStateOf("")
    }
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter)
        },
        content = {
            Input(
                type = InputType.Text,
                attrs = {
                    classes(CommonStyles.p70, CommonStyles.textInput)
                    placeholder("输入用户ID搜索")
                    onChange {
                        content = it.value
                    }
                }
            )
            HorizontalDivider(width = 12.px)
            PrimaryButton(
                text = if (loading) "查询中..." else "查看",
                enabled = enable,
                onClick = {
                    onClickSearch(content)
                },
                customStyle = {
                    paddingTop(8.px)
                    paddingBottom(8.px)
                },
            )
        }
    )
}
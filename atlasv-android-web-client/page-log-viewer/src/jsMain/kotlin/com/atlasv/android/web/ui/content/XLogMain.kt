package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.common.constant.getXLogStorageBaseUrl
import com.atlasv.android.web.data.model.QueryRecord
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
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
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
    var historyUidList by remember {
        mutableStateOf<List<QueryRecord>>(emptyList())
    }
    val recentlyUidList = historyUidList.take(10)
    Style(CommonStyles)
    Style(TextStyles)
    val apps = listOf(AppEnum.Ins3, AppEnum.Fbd2, AppEnum.Ttd1, AppEnum.Ttd2)
    var inputContent by remember {
        mutableStateOf("")
    }
    val queryUidAction: (appPackage: String) -> Unit = { appPackage ->
        CoroutineScope(Dispatchers.Default).launch {
            historyUidList =
                historyUidList.takeIf { uidRecord -> uidRecord.any { it.appPackage == appPackage } }.orEmpty()
            historyUidList =
                XLogRepository.instance.queryHistoryUidList(appPackage = currentApp?.packageName)?.data.orEmpty()
        }
    }

    val queryLogsAction: (String) -> Unit = { uid: String ->
        CoroutineScope(Dispatchers.Default).launch {
            loading = true
            xLogResponse = XLogRepository.instance.queryLogs(uid = uid, currentApp?.packageName)
            loading = false
        }
    }

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
                        currentApp?.also {
                            queryUidAction(it.packageName)
                        }
                    }
                },
                selectedIndex = currentApp?.let {
                    apps.indexOf(it)
                } ?: -1
            )
            if (currentApp == null) {
                VerticalDivider(height = 6.px)
                ErrorTip("还没有选择App")
            } else {
                VerticalDivider(height = 6.px)
                Link(text = "所有日志", url = currentApp?.getXLogStorageBaseUrl().orEmpty())
            }
            VerticalDivider(height = 16.px)
            TextInputView(
                inputContent,
                onClickSearch = { content ->
                    queryLogsAction(content)
                },
                loading = loading,
                enable = !loading && currentApp != null
            )

            if (recentlyUidList.isNotEmpty()) {
                VerticalDivider(height = 16.px)
                Title("历史查询Uid")
                VerticalDivider(height = 6.px)
                recentlyUidList.forEach {
                    HistoryUidItemView(it.uid, onClick = { uid ->
                        inputContent = uid
                    })
                }
            }

            XLogListView(xLogResponse)
        }
    )
}

@Composable
private fun Link(text: String, url: String) {
    Div({
        classes(TextStyles.textBlue, TextStyles.text1)
        style {
            paddingLeft(6.px)
            paddingRight(6.px)
        }
    }) {
        A(
            attrs = {
                target(ATarget.Blank)
            },
            href = url
        ) {
            Text(text)
        }
    }
}

@Composable
private fun Title(text: String) {
    Div(
        attrs = {
            classes(TextStyles.text1)
        },
        content = {
            Text(text)
        }
    )
}

@Composable
private fun HistoryUidItemView(text: String, onClick: (uid: String) -> Unit) {
    Div(
        attrs = {
            classes(TextStyles.text2)
            style {
                paddingTop(3.px)
                paddingBottom(3.px)
            }
            onClick {
                onClick(text)
            }
        },
        content = {
            Text(text)
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
private fun TextInputView(inputContent: String, onClickSearch: (String) -> Unit, loading: Boolean, enable: Boolean) {
    var content by remember(inputContent) { mutableStateOf(inputContent) }
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
                    defaultValue(inputContent)
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
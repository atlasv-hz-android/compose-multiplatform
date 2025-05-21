package com.atlasv.android.web.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.common.constant.getXLogStorageBaseUrl
import com.atlasv.android.web.data.model.QueryRecord
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.model.VipInfo
import com.atlasv.android.web.data.repo.XLogRepository
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.PrimaryButton
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.browser.window
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
import org.jetbrains.compose.web.dom.Br
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
    val xLogRepository by remember {
        mutableStateOf(XLogRepository.create(windowHrefUrl = window.location.href))
    }
    var xLogResponse by remember { mutableStateOf<StorageObjectResponse?>(null) }
    var vipInfo by remember { mutableStateOf<VipInfo?>(null) }
    var loading by remember { mutableStateOf(false) }
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    var historyUidList by remember {
        mutableStateOf<List<QueryRecord>>(emptyList())
    }
    val recentlyUidList = historyUidList.filter {
        it.appPackage == currentApp?.packageName
    }.take(20)
    Style(CommonStyles)
    Style(TextStyles)
    val apps = AppEnum.entries
    var inputContent by remember {
        mutableStateOf("")
    }
    val queryUidAction: () -> Unit = {
        CoroutineScope(Dispatchers.Default).launch {
            historyUidList = xLogRepository.queryHistoryUidList()?.data.orEmpty()
        }
    }

    val queryVipInfoAction: (String) -> Unit = { uid: String ->
        CoroutineScope(Dispatchers.Default).launch {
            vipInfo = xLogRepository.queryVipInfo(userId = uid)
        }
    }

    val queryLogsAction: (String) -> Unit = { uid: String ->
        CoroutineScope(Dispatchers.Default).launch {
            loading = true
            xLogResponse = xLogRepository.queryLogs(uid = uid, currentApp?.packageName)
            loading = false
            if (!xLogResponse?.items.isNullOrEmpty()) {
                queryUidAction()
            }
            queryVipInfoAction(uid)
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
            VipInfo(vipInfo)
            if (recentlyUidList.isNotEmpty()) {
                VerticalDivider(height = 16.px)
                Title("历史查询Uid")
                VerticalDivider(height = 6.px)
                HistoryUidListView(recentlyUidList, onClick = { uid ->
                    inputContent = uid
                })
            }
            XLogListView(xLogResponse, buildDownloadUrl = xLogRepository::buildDownloadUrl)
        }
    )
    LaunchedEffect(Unit) {
        queryUidAction()
    }
}

@Composable
private fun VipInfo(info: VipInfo?) {
    info ?: return
    VerticalDivider(height = 16.px)
    Div(
        attrs = {
            classes(TextStyles.text2)
        },
        content = {
            Text("订单号：${info.TransactionID}")
            Br()
            Text("初始订单号：${info.original_transaction_id}")
            Br()
            Text("购买时间：${info.purchase_date}")
            Br()
            Text("过期时间：${info.expires_date}")
            Br()
            Text("退款时间：${info.cancellation_date_ms}")
            Br()
            Text("权益ID：${info.entitlement_id}")
            Br()
            Text("自动续订：${info.auto_renew}")
            Br()
            Text("自动续订状态变更时间：${info.auto_renew_status_change_date_ms}")
            Br()
            Text("是否处于体验价：${info.is_in_intro_offer_period}")
            Br()
            Text("是否处于试用期：${info.is_in_trial_period}")
            Br()
            Text("支付状态：${info.payment_state}")
            Br()
            Text("商品ID：${info.product_identifier}")
            Br()
            Text("用户ID：${info.user_id}")
        }
    )
}

@Composable
private fun HistoryUidListView(queryRecords: List<QueryRecord>, onClick: (String) -> Unit) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            queryRecords.forEach {
                Div(
                    attrs = {
                        style {
                            paddingLeft(4.px)
                            paddingRight(4.px)
                            paddingBottom(8.px)
                        }
                    },
                    content = {
                        HistoryUidItemView(it.uid, onClick = { uid ->
                            onClick(uid)
                        })
                    })
            }
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
            classes(CommonStyles.historyFlowItem)
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
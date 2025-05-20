package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.StorageObject
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.model.XlogStorageItem
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/11
 */

@Composable
fun XLogListView(data: StorageObjectResponse?, buildDownloadUrl: (XlogStorageItem, asAttachment: Boolean) -> String) {
    data ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("日志列表")
    }
    data.items.forEach {
        XLogListItemView(it, data.appPackage, buildDownloadUrl)
    }
}

@Composable
private fun XLogListItemView(
    item: StorageObject,
    appPackage: String?,
    buildDownloadUrl: (XlogStorageItem, asAttachment: Boolean) -> String
) {
    VerticalDivider(6.px)
    Div({
        classes(TextStyles.text2)
        classes(CommonStyles.horizontal)
    }) {
        Text("${item.path}(${item.size} B)")
        HorizontalDivider(width = 10.px)
        listOf(
            "下载文件" to buildDownloadUrl(XlogStorageItem(appPackage.orEmpty(), item.path), true),
            "在线查看" to buildDownloadUrl(XlogStorageItem(appPackage.orEmpty(), item.path), false),
        ).take(if (item.size < 5 * 1024 * 1024) 2 else 1).forEach { (text, url) ->
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
    }
}
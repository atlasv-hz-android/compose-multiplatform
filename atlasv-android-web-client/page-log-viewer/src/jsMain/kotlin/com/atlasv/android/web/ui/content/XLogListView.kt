package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.StorageObject
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import io.ktor.http.encodeURLQueryComponent
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
fun XLogListView(data: StorageObjectResponse?) {
    data ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("日志列表")
    }
    data.items.forEach {
        XLogListItemView(it)
    }
}

@Composable
private fun XLogListItemView(item: StorageObject) {
    VerticalDivider(6.px)
    Div({
        classes(TextStyles.text2)
        classes(CommonStyles.horizontal)
    }) {
        Text("${item.path}(${item.size} B)")
        HorizontalDivider(width = 10.px)
        listOf(
            "下载文件" to "${HttpEngine.baseUrl}download_xlog?blob_name=${item.path.encodeURLQueryComponent()}&app_package=${AppEnum.Ins3.packageName}&download=1",
            "在线查看" to "${HttpEngine.baseUrl}download_xlog?blob_name=${item.path.encodeURLQueryComponent()}&app_package=${AppEnum.Ins3.packageName}",
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
package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.data.model.StorageObject
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import io.ktor.http.encodeURLQueryComponent
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/11
 */

@Composable
fun XLogListView(data: StorageObjectResponse?, onClick: (StorageObject) -> Unit) {
    data ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("日志列表")
    }
    data.items.forEach {
        XLogListItemView(it, onClick)
    }
}

@Composable
private fun XLogListItemView(item: StorageObject, onClick: (StorageObject) -> Unit) {
    VerticalDivider(6.px)
    Div({
        classes(TextStyles.text2)
        classes(CommonStyles.horizontal)
    }) {
        Text("${item.path}(${item.size} B)")
        Div({
            classes(TextStyles.textBlue)
            onClick {
                onClick(item)
            }
        }) {

            A(
                attrs = {
                    target(ATarget.Blank)
                },
                href = "${HttpEngine.baseUrl}download_xlog?blob_name=${item.path.encodeURLQueryComponent()}"
            ) {
                Text("(下载)")
            }
        }
    }
}
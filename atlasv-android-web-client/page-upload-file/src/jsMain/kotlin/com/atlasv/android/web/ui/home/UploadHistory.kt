package com.atlasv.android.web.ui.home

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.UploadRecordData
import com.atlasv.android.web.data.model.UploadRecordItem
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/11
 */

@Composable
fun UploadHistory(data: UploadRecordData?) {
    data?.items?.takeIf { it.isNotEmpty() } ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("最近上传")
    }
    VerticalDivider(12.px)
    data.items.forEach {
        Div({
            classes(TextStyles.subText)
        }) {
            Text(it.createAt)
        }
        UploadRecordItemView(it, onClick = {
            window.open(url = it.fileUrl, target = ATarget.Blank.targetStr)
        })
    }
}

@Composable
private fun UploadRecordItemView(item: UploadRecordItem, onClick: () -> Unit) {
    Div(
        attrs = {
            classes(TextStyles.text3)
            onClick {
                onClick()
            }
        },
    ) {
        Text(item.fileUrl)
    }
    VerticalDivider(12.px)
}
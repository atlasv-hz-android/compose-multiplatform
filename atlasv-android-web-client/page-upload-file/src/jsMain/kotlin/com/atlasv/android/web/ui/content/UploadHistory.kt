package com.atlasv.android.web.ui.content

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
    data ?: return
    Div({
        classes(TextStyles.text2)
        style { paddingTop(32.px) }
    }) {
        Text("最近上传")
    }
    data.items.forEach {
        UploadRecordItemView(it, onClick = {
            window.open(url = it.fileUrl, target = ATarget.Blank.targetStr)
        })
    }
}

@Composable
private fun UploadRecordItemView(item: UploadRecordItem, onClick: () -> Unit) {
    VerticalDivider(4.px)
    Div(
        attrs = {
            classes(TextStyles.text5)
            onClick {
                onClick()
            }
        },
    ) {
        Text(item.fileUrl)
    }
}
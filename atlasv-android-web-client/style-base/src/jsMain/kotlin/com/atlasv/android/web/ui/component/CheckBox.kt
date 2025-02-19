package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/19
 */

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
        }
    ) {
        Input(
            type = InputType.Checkbox,
            attrs = {
                classes(CommonStyles.checkBox)
                checked(checked)
                onClick { onCheckedChange(!checked) }
            }
        )
        Label {
            Text(label)
        }
    }
}
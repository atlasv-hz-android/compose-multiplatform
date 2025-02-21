@file:Suppress("UNUSED_VARIABLE")

package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.data.model.StorageObjectResponse
import com.atlasv.android.web.data.repo.ProductRepository
import com.atlasv.android.web.data.repo.XLogRepository
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    var xLogResponse by remember { mutableStateOf<StorageObjectResponse?>(null) }
    var productResponse by remember { mutableStateOf<ProductResponse?>(null) }
    var loading by remember { mutableStateOf(false) }
    Style(CommonStyles)
    Style(TextStyles)
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                paddingLeft(16.px)
                paddingRight(16.px)
            }
        },
        content = {
            XLogListView(xLogResponse, onClick = {

            })
            ProductListView(productResponse, onClick = {

            })
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                xLogResponse = XLogRepository.instance.queryLogs()
            }
            launch {
                productResponse = ProductRepository.instance.queryProducts()
            }
        }
    }
}


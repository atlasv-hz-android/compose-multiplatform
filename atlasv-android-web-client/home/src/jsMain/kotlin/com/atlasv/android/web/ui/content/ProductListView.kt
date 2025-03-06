package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import com.atlasv.android.web.data.model.ProductEntity
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/11
 */
@Composable
fun ProductListView(data: ProductResponse?, onClick: (ProductEntity) -> Unit) {
    data ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("商品列表")
    }
    data.products.forEach {
        ProductListItemView(it, onClick)
    }
}

@Composable
private fun ProductListItemView(item: ProductEntity, onClick: (ProductEntity) -> Unit) {
    VerticalDivider(6.px)
    Div({
        classes(TextStyles.text2)
        classes(CommonStyles.horizontal)
    }) {
        Text(item.productId)
    }
}
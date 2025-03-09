package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.data.model.ProductEntity
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.data.repo.ProductRepository
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

private fun queryProducts(appPackage: String?, onResult: (ProductResponse) -> Unit) {
    appPackage ?: return
    CoroutineScope(Dispatchers.Default).launch {
        onResult(ProductRepository.instance.queryProducts(appPackage))
    }
}

@Composable
fun Body() {
    var productResponse by remember { mutableStateOf<ProductResponse?>(null) }
    Style(CommonStyles)
    Style(TextStyles)
    val apps = AppEnum.entries
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
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
                        productResponse = null
                        queryProducts(
                            currentApp?.packageName,
                            onResult = {
                                productResponse = it
                            }
                        )
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
            }
            VerticalDivider(height = 16.px)
            ProductListView(productResponse)
        }
    )
}

@Composable
private fun ProductListView(productResponse: ProductResponse?) {
    productResponse ?: return
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            productResponse.products.forEach {
                Div(attrs = {
                    style {
                        paddingLeft(4.px)
                        paddingRight(4.px)
                        paddingBottom(8.px)
                    }
                },
                    content = {
                        ProductItemView(it)
                    })
            }
        }
    )
}

@Composable
private fun ProductItemView(productEntity: ProductEntity) {
    Div(
        attrs = {
            classes(CommonStyles.historyFlowItem)
            style {
                borderRadius(16.px)
            }
            onClick {

            }
        },
        content = {
            Div(
                attrs = {
                    classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
                },
                content = {
                    Div(attrs = {
                        classes(TextStyles.text2)
                    }, content = {
                        Text(productEntity.productId)
                    })

                    Div(attrs = {
                        classes(TextStyles.subText)
                    }, content = {
                        Text(productEntity.entitlementId)
                    })
                }
            )
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


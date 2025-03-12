package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.common.constant.getProductApiUrlV2
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.data.repo.ProductRepository
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.table.TableView
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
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
                Link(text = "所有商品(v2)", url = currentApp?.getProductApiUrlV2().orEmpty())
                VerticalDivider(height = 6.px)
                AddProductTip(currentApp)
            }
            VerticalDivider(height = 16.px)
            ProductListView(productResponse)
        }
    )
}

@Composable
private fun AddProductTip(appEnum: AppEnum?) {
    appEnum ?: return
    Div(
        attrs = {
            classes(TextStyles.subText)
        },
        content = {
            Text("添加商品使用(填写product_id和entitlement_id)：")
        }
    )
    Div(
        attrs = {
            classes(TextStyles.subText)
        },
        content = {
            Text("https://atlasv-android-team.uc.r.appspot.com/api/purchase/add_products?app_package=${appEnum.packageName}&product_id=&entitlement_id=")
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
private fun ProductListView(productResponse: ProductResponse?) {
    productResponse ?: return
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
            style {
                paddingTop(40.px)
            }
        },
        content = {
            TableView(model = productResponse.asTableModel(), smallTextMode = false)
        })
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


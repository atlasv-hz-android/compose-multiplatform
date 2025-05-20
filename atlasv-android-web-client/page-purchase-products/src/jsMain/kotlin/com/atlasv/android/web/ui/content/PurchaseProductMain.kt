package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.constant.AppEnum
import com.atlasv.android.web.common.constant.getProductApiUrlV2
import com.atlasv.android.web.data.model.ProductOperationRecord
import com.atlasv.android.web.data.model.ProductOperationRecordResponse
import com.atlasv.android.web.data.model.ProductResponse
import com.atlasv.android.web.data.repo.ProductRepository
import com.atlasv.android.web.ui.component.AppTabLayout
import com.atlasv.android.web.ui.component.PrimaryButton
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.component.dialog.ModalDialog
import com.atlasv.android.web.ui.component.table.TableView
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

private fun queryProducts(
    productRepository: ProductRepository,
    appPackage: String?,
    onResult: (ProductResponse) -> Unit
) {
    appPackage ?: return
    CoroutineScope(Dispatchers.Default).launch {
        onResult(productRepository.queryProducts(appPackage))
    }
}

private fun getProductOperationRecords(
    productRepository: ProductRepository,
    appPackage: String?,
    onResult: (ProductOperationRecordResponse) -> Unit
) {
    appPackage ?: return
    CoroutineScope(Dispatchers.Default).launch {
        onResult(productRepository.getProductOperationRecords(appPackage))
    }
}

private fun addProduct(
    productRepository: ProductRepository,
    appPackage: String,
    productId: String,
    entitlementId: String,
    onResult: (String) -> Unit
) {
    CoroutineScope(Dispatchers.Default).launch {
        onResult(productRepository.addProduct(appPackage, productId, entitlementId))
    }
}

private fun deleteProduct(
    productRepository: ProductRepository,
    appPackage: String,
    productId: String,
    onResult: (String) -> Unit
) {
    CoroutineScope(Dispatchers.Default).launch {
        onResult(productRepository.deleteProduct(appPackage, productId))
    }
}

@Composable
fun Body() {
    val productRepository by remember {
        mutableStateOf(
            ProductRepository.create(windowHref = window.location.href)
        )
    }

    var productResponse by remember { mutableStateOf<ProductResponse?>(null) }
    var _operationRecords by remember { mutableStateOf<ProductOperationRecordResponse?>(null) }
    val apps = AppEnum.entries
    var currentApp by remember {
        mutableStateOf<AppEnum?>(null)
    }
    val operationRecords = _operationRecords?.takeIf { it.appPackage == currentApp?.packageName }?.records
    var showAddProductDialog by remember {
        mutableStateOf(false)
    }

    var showDetailProductId by remember {
        mutableStateOf("")
    }
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
            VerticalDivider(height = 20.px)
            Text(
                value = window.location.href
            )
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
                            productRepository,
                            currentApp?.packageName,
                            onResult = {
                                productResponse = it
                            }
                        )
                        getProductOperationRecords(productRepository, currentApp?.packageName) {
                            _operationRecords = it
                        }
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
            }
            VerticalDivider(height = 16.px)
            ProductListView(productResponse, onClickProduct = {
                showDetailProductId = it
            })
            if (currentApp != null) {
                AddProductButton(onClick = {
                    showAddProductDialog = true
                })
            }
            ProductOperationListView(operationRecords)
        }
    )
    if (showAddProductDialog) {
        AddProductDialog(currentApp?.packageName, onConfirm = { appPackage, productId, entitlementId ->
            addProduct(productRepository, appPackage, productId, entitlementId) {
                getProductOperationRecords(productRepository, appPackage) {
                    _operationRecords = it
                    showAddProductDialog = false
                }
            }
        }, onDismiss = {
            showAddProductDialog = false
        })
    }
    if (showDetailProductId.isNotEmpty()) {
        ProductDetailDialog(currentApp?.packageName, showDetailProductId, onConfirm = { appPackage, productId ->
            deleteProduct(productRepository, appPackage, productId) {
                getProductOperationRecords(productRepository, appPackage) {
                    _operationRecords = it
                    showDetailProductId = ""
                }
            }
        }, onDismiss = {
            showDetailProductId = ""
        })
    }
}

@Composable
private fun ProductDetailDialog(
    appPackage: String?,
    productId: String,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    appPackage ?: return
    ModalDialog {
        Div(
            attrs = {
                classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
                style {
                    width(480.px)
                }
            },
            content = {
                Label(
                    attrs = {
                        style {
                            color(CommonColors.red)
                            fontSize(24.px)
                            fontWeight(500)
                        }
                    },
                    content = {
                        Text("删除商品")
                    }
                )
                Label(
                    attrs = {
                        classes(TextStyles.text3)
                    },
                    content = {
                        Text(appPackage)
                    })

                VerticalDivider(height = 16.px)

                Label(
                    attrs = {
                        classes(TextStyles.text1)
                    },
                    content = {
                        Text("确认删除商品${productId}?")
                    })

                VerticalDivider(height = 32.px)
                PrimaryButton(
                    text = "确定",
                    enabled = true,
                    onClick = {
                        if (productId.isNotEmpty()) {
                            onConfirm(appPackage, productId)
                        }
                    },
                    customStyle = {
                        width(360.px)
                        paddingTop(8.px)
                        paddingBottom(8.px)
                    }
                )
                VerticalDivider(height = 16.px)
                PrimaryButton(
                    text = "取消",
                    enabled = true,
                    onClick = onDismiss,
                    customStyle = {
                        width(360.px)
                        paddingTop(8.px)
                        paddingBottom(8.px)
                    }
                )
            }
        )
    }
}

@Composable
private fun AddProductButton(onClick: () -> Unit) {
    VerticalDivider(height = 20.px)
    Div(attrs = {
        classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter, CommonStyles.alignItemsCenter)
    }, content = {
        PrimaryButton(
            text = "新增商品",
            enabled = true,
            onClick = onClick,
            customStyle = {
                width(360.px)
                paddingTop(8.px)
                paddingBottom(8.px)
            }
        )
    })
}

@Composable
private fun AddProductDialog(
    appPackage: String?,
    onConfirm: (appPackage: String, productId: String, entitlementId: String) -> Unit,
    onDismiss: () -> Unit,
) {
    appPackage ?: return
    var productIdInput by remember { mutableStateOf("") }
    var entitlementIdInput by remember { mutableStateOf("") }
    ModalDialog() {
        Div(
            attrs = {
                classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
                style {
                    width(480.px)
                }
            },
            content = {
                Label(
                    attrs = {
                        style {
                            color(CommonColors.primaryBlue)
                            fontSize(24.px)
                            fontWeight(500)
                        }
                    },
                    content = {
                        Text("新增商品")
                    }
                )
                Label(
                    attrs = {
                        classes(TextStyles.text3)
                    },
                    content = {
                        Text(appPackage)
                    })

                VerticalDivider(height = 36.px)

                Label(
                    attrs = {
                        style {
                            width(90.percent)
                        }
                    },
                    content = {
                        Text("商品ID")
                    })
                VerticalDivider(height = 4.px)
                Input(
                    type = InputType.Text,
                    attrs = {
                        style {
                            width(90.percent)
                            paddingTop(8.px)
                            paddingBottom(8.px)
                            paddingLeft(12.px)
                            paddingRight(12.px)
                        }
                        value(productIdInput)
                        required()
                        onInput {
                            productIdInput = it.value
                        }
                    }
                )
                VerticalDivider(height = 24.px)
                Label(
                    attrs = {
                        style {
                            width(90.percent)
                        }
                    },
                    content = {
                        Text("权益ID")
                    })
                VerticalDivider(height = 4.px)
                Input(
                    type = InputType.Text,
                    attrs = {
                        style {
                            width(90.percent)
                            paddingTop(8.px)
                            paddingBottom(8.px)
                            paddingLeft(12.px)
                            paddingRight(12.px)
                        }
                        value(entitlementIdInput)
                        required()
                        onInput {
                            entitlementIdInput = it.value
                        }
                    }
                )
                VerticalDivider(height = 32.px)
                PrimaryButton(
                    text = "确定",
                    enabled = true,
                    onClick = {
                        if (productIdInput.isNotEmpty() && entitlementIdInput.isNotEmpty()) {
                            onConfirm(appPackage, productIdInput, entitlementIdInput)
                        }
                    },
                    customStyle = {
                        width(360.px)
                        paddingTop(8.px)
                        paddingBottom(8.px)
                    }
                )
                VerticalDivider(height = 16.px)
                PrimaryButton(
                    text = "取消",
                    enabled = true,
                    onClick = onDismiss,
                    customStyle = {
                        width(360.px)
                        paddingTop(8.px)
                        paddingBottom(8.px)
                    }
                )
            }
        )
    }
}

@Composable
private fun ProductOperationListView(records: List<ProductOperationRecord>?) {
    records?.takeIf { it.isNotEmpty() } ?: return
    Div({
        classes(TextStyles.text1)
        style { paddingTop(32.px) }
    }) {
        Text("操作记录")
    }
    VerticalDivider(6.px)
    records.forEach {
        Div({
            classes(TextStyles.subText)
        }) {
            Text(it.createAt)
        }
        OperationRecordItemView(it)
    }
}

@Composable
private fun OperationRecordItemView(item: ProductOperationRecord) {
    Div(
        attrs = {
            classes(TextStyles.text3)
        },
    ) {
        Text(item.description)
    }
    VerticalDivider(8.px)
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
private fun ProductListView(productResponse: ProductResponse?, onClickProduct: (productId: String) -> Unit) {
    productResponse ?: return
    Div(
        attrs = {
            classes(CommonStyles.vertical, CommonStyles.alignItemsCenter)
            style {
                paddingTop(40.px)
            }
        },
        content = {
            TableView(
                model = productResponse.asTableModel(),
                smallTextMode = false,
                cellContentBuilder = { index, item ->
                    CellContent(index, item, onClick = { onClickProduct(item.text) })
                })
        })
}

@Composable
private fun CellContent(index: Int, model: TableCellModel, onClick: () -> Unit) {
    if (index == 0) { // 商品列
        Div(
            attrs = {
                style {
                    fontSize(14.px)
                    cursor("pointer")
                }
                onClick {
                    onClick()
                }
            },
            content = {
                Text(model.text)
            }
        )
    } else {
        Text(model.text)
    }
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


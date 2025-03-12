package com.atlasv.android.web.data.model

import com.atlasv.android.web.ui.model.TableCellModel
import com.atlasv.android.web.ui.model.TableModel
import com.atlasv.android.web.ui.model.TableRowModel
import kotlinx.serialization.Serializable

/**
 * Created by weiping on 2025/2/13
 */
@Serializable
data class ProductResponse(val products: List<ProductEntity>) {
    fun asTableModel(): TableModel {
        val headRow = TableRowModel(
            cells = listOf(
                TableCellModel(
                    text = "商品ID", isHeaderCell = true
                ),
                TableCellModel(
                    text = "权益ID", isHeaderCell = true
                )
            ),
            isHeader = true
        )
        val rows = listOf(headRow) + products.map {
            it.asRowModel()
        }
        return TableModel(
            rows = rows,
            spanCount = 2
        )
    }
}

package com.atlasv.android.web.ui.component

import androidx.compose.runtime.Composable
import com.atlasv.android.web.ui.model.TabItemData
import com.atlasv.android.web.ui.style.CommonStyles
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

/**
 * Created by weiping on 2025/2/26
 */

@Composable
fun AppTabLayout(
    items: List<TabItemData>,
    onItemClick: (Int) -> Unit,
    selectedIndex: Int
) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
        },
        content = {
            items.forEachIndexed { index, tabItemData ->
                Div(attrs = {
                    classes(CommonStyles.p10)
                    onClick {
                        onItemClick(tabItemData.id)
                    }
                }, content = {
                    TabItem(tabItemData, selected = index == selectedIndex)
                })
            }
        }
    )
}

@Composable
fun TabItem(itemData: TabItemData, selected: Boolean) {
    Div(attrs = {
        classes(CommonStyles.vertical, CommonStyles.alignItemsCenter, CommonStyles.justifyContentCenter)
        classes(if (selected) CommonStyles.tabItem else CommonStyles.tabItemUnSelected)
        style {
            width(90.percent)
            fontWeight(if (selected) 500 else 400)
        }
    }, content = {
        Text(itemData.text)
    })
}


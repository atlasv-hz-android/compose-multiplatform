package com.atlasv.android.web.ui.component.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.ui.model.TabModel
import com.atlasv.android.web.ui.style.TabStyle
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

/**
 * Created by weiping on 2025/3/27
 */
@Composable
fun TabContainer(
    models: List<TabModel>,
    onTabClick: (TabModel) -> Unit,
    tabContentBuilder: ContentBuilder<HTMLDivElement>
) {
    Div(
        attrs = {
            classes(TabStyle.tabContainer)
        },
        content = {
            TabHeader(models, onTabClick)
            TabContent(tabContentBuilder)
        }
    )
}

@Composable
fun TabContent(tabContentBuilder: ContentBuilder<HTMLDivElement>) {
    Div(
        attrs = {
            classes(
                TabStyle.tabContent
            )
        },
        content = tabContentBuilder
    )
}

@Composable
fun TabHeader(models: List<TabModel>, onTabClick: (TabModel) -> Unit) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    Div(
        attrs = {
            classes(TabStyle.tabHeader)
        },
        content = {
            models.forEachIndexed { index, tabModel ->
                Button(
                    attrs = {
                        classes(if (selectedIndex == index) TabStyle.tabButtonActive else TabStyle.tabButton)
                        onClick {
                            selectedIndex = index
                            onTabClick(tabModel)
                        }
                    },
                    content = {
                        Text(tabModel.text)
                    }
                )
            }
        }
    )
}

package com.atlasv.android.web.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.atlasv.android.web.common.HttpEngine
import com.atlasv.android.web.common.data.model.User
import com.atlasv.android.web.common.data.repo.UserRepository
import com.atlasv.android.web.ui.component.HorizontalDivider
import com.atlasv.android.web.ui.component.MaterialCardGrid
import com.atlasv.android.web.ui.component.VerticalDivider
import com.atlasv.android.web.ui.style.CommonColors
import com.atlasv.android.web.ui.style.CommonStyles
import com.atlasv.android.web.ui.style.TextStyles
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Composable
fun Body() {
    Style(CommonStyles)
    Style(TextStyles)
    HomeModuleGroups()
}

@Composable
private fun HomeModuleGroups() {
    var user by remember {
        mutableStateOf<User?>(null)
    }
    Div(
        attrs = {
            classes(CommonStyles.vertical)
        },
        content = {
            UserInfoCard(user)
            VerticalDivider(height = 20.px)
            HomeFunctionModuleGroup.homeModuleGroups.forEach {
                VerticalDivider(height = 36.px)
                FunctionCardsContainer(it)
            }
        }
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            user = UserRepository.instance.getUser()
        }
    }
}

@Composable
private fun UserInfoCard(user: User?) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, TextStyles.text3)
            style {
                paddingLeft(16.px)
                paddingTop(8.px)
            }
        },
        content = {
            val text = user?.name?.let {
                "Welcome, ${user.name}"
            } ?: "Welcome"
            Text(text)
            if (user != null) {
                HorizontalDivider(width = 6.px)
                LogoutView()
            }
            HorizontalDivider(width = 8.px)
            Wasm()
        }
    )
}

@Composable
private fun LogoutView() {
    A(href = "${HttpEngine.computeEngineUrl}/logout") {
        Text("登出")
    }
}

@Composable
private fun Wasm() {
    A(href = "${HttpEngine.createApiBaseUrlByWindowHref(window.location.href)}/home/") {
        Text("切到Wasm版本(Alpha)")
    }
}


@Composable
private fun GroupTitle(text: String) {
    Div(
        attrs = {
            classes(CommonStyles.horizontal, CommonStyles.justifyContentCenter)
            style {
                paddingLeft(12.px)
            }
        },
        content = {
            Div(attrs = {
                classes(CommonStyles.p70)
                style {
                    color(CommonColors.mainTextColor)
                    fontSize(20.px)
                }
            }, content = {
                Text(value = text)
            })
        }
    )
}

@Composable
private fun FunctionCardsContainer(group: HomeFunctionModuleGroup) {
    GroupTitle(group.title)
    VerticalDivider(height = 2.px)
    Div(
        attrs = {
            classes(CommonStyles.horizontal)
            classes(CommonStyles.justifyContentCenter)
        },
        content = {
            FunctionCards(group.modules)
        }
    )
}

@Composable
private fun FunctionCards(modules: List<HomeFunctionModule>) {
    Div(
        attrs = {
            classes(CommonStyles.horizontalFlow)
            classes(CommonStyles.p70)
        },
        content = {
            modules.forEach {
                MaterialCardGrid(
                    content = {
                        CardContent(title = it.title, desc = it.desc)
                    },
                    onClick = {
                        window.open(url = it.targetUrl, target = ATarget.Blank.targetStr)
                    }
                )
            }
        }
    )
}

@Composable
private fun CardContent(title: String, desc: String = "") {
    Div(
        attrs = {
            classes(CommonStyles.vertical)
            style {
                width(100.percent)
                height(100.percent)
            }
        },
        content = {
            CardTitle(title = title)
            CardSubTitle(text = desc)
        }
    )
}

@Composable
private fun CardTitle(title: String) {
    Div(
        attrs = {
            classes(TextStyles.text1)
        },
        content = {
            Text(value = title)
        }
    )
}

@Composable
private fun CardSubTitle(text: String) {
    if (text.isEmpty()) {
        return
    }
    VerticalDivider(height = 10.px)
    Div(
        attrs = {
            classes(TextStyles.subText)
        },
        content = {
            Text(value = text)
        }
    )
}
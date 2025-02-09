package com.sample

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sample.components.Layout
import com.sample.style.AppStylesheet
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

@Serializable
data class Data(val name: String, val age: Int)

suspend fun fetchData(url: String): Data {
    val response = window.fetch(url).await()
    val text = response.text().await()
    console.log(text)
    return Json.decodeFromString(text)
}

fun main() {
    renderComposable(rootElementId = "root") {
        Style(AppStylesheet)
        var selectedFile by remember { mutableStateOf<org.w3c.files.File?>(null) }
        val data = remember { mutableStateOf<Data?>(null) }

        Layout {
//            Header()
//            MainContentLayout {
//                Intro()
//                ComposeWebLibraries()
//                GetStarted()
//                CodeSamples()
//                JoinUs()
//            }
//            PageFooter()
            Input(
                type = InputType.File,
                attrs = {
                    onChange {
                        selectedFile = it.target.files?.item(0)
                    }
                }
            )
            if (selectedFile != null) {
                Text("Selected File: ${selectedFile?.name}")
            }
            Text("text=${data.value}")
        }

        LaunchedEffect(Unit) {
//            data.value = fetchData("http://127.0.0.1:8080/get_item")
            data.value = fetchData("https://fx-editor.ue.r.appspot.com/get_item")
        }
    }
}
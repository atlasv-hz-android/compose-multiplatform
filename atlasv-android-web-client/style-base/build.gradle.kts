import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

// Kotlin Multiplatform Wizard
// https://kmp.jetbrains.com/?web=true&includeTests=true

plugins {
    kotlin("plugin.serialization") version libs.versions.kotlin // 插件版本需要和 Kotlin 版本匹配
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
//            api(compose.foundation)
//            api(compose.material3)
//            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtimeCompose)
        }
        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.runtime)
        }
    }
}

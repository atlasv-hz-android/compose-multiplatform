plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.22" // 插件版本需要和 Kotlin 版本匹配
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("highlight.js", "10.7.2"))
                implementation(compose.html.core)
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
            }
        }
    }
}

fun publishWebSite() {
    val productionExecutableDir = project.file("build/dist/js/productionExecutable/")
    val indexFile = File(productionExecutableDir, "index.html")
    copy {
        from(indexFile.also {
            var indexContent = indexFile.readText()
            indexContent = indexContent.replace("\"${project.name}.js\"", "\"static/js/${project.name}.js\"")
            productionExecutableDir.listFiles { file ->
                file.name.endsWith(".css")
            }?.forEach { cssFile ->
                indexContent =
                    indexContent.replace("\"${cssFile.name}\"", "\"static/${project.name}/${cssFile.name}\"")
                indexContent =
                    indexContent.replace("\"/${cssFile.name}\"", "\"static/${project.name}/${cssFile.name}\"")
            }
            it.writeText(indexContent)
        })
        into("../../atlasv-android-web/templates/")
        rename {
            it.replaceBeforeLast(".", project.name)
        }
    }
    val jsFile = File(productionExecutableDir, "${project.name}.js")

    copy {
        from(jsFile.also {
            var jsFileContent = jsFile.readText()
            productionExecutableDir.listFiles { file ->
                file.name != jsFile.name && file.name != indexFile.name
            }?.forEach {
                jsFileContent = jsFileContent.replace("\"${it.name}\"", "\"static/${project.name}/${it.name}\"")
            }
            jsFile.writeText(jsFileContent)
        })
        into("../../atlasv-android-web/static/js")
    }
    copy {
        from(productionExecutableDir) {
            exclude("index.html")
            exclude(jsFile.name)
            exclude(jsFile.name + ".map")
        }
        into("../../atlasv-android-web/static/${project.name}")
    }
}

afterEvaluate {
    tasks.findByName("jsBrowserProductionWebpack")?.doLast {
        publishWebSite()
    }
}

plugins {
    kotlin("multiplatform") apply false
    id("com.github.ben-manes.versions") version "0.49.0"
}

subprojects {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
        }
        maven {
            url = uri("https://packages.jetbrains.team/maven/p/ui/dev")
        }
        google()
    }
}

private val webVersion = 6

subprojects {
    fun publishWebSite(backendProjectName: String) {
        val productionExecutableDir = project.file("build/dist/js/productionExecutable/")
        val indexFile = File(productionExecutableDir, "index.html")
        if (!indexFile.exists()) {
            return
        }
        val jsFile = File(productionExecutableDir, "${project.name}.js")
        if (!jsFile.exists()) {
            return
        }
        val targetJsFileName = "${project.name}_${webVersion}.js"
        copy {
            from(indexFile.also {
                var indexContent = indexFile.readText()
                indexContent = indexContent.replace("\"${project.name}.js\"", "\"static/js/${targetJsFileName}\"")
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
            into("../../../${backendProjectName}/templates/")
            rename {
                it.replaceBeforeLast(".", project.name)
            }
        }

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
            into("../../../${backendProjectName}/static/js")
            rename(jsFile.name, targetJsFileName)
        }
        copy {
            from(productionExecutableDir) {
                exclude("index.html")
                exclude(jsFile.name)
                exclude(jsFile.name + ".map")
            }
            into("../../../${backendProjectName}/static/${project.name}")
        }
    }

    if (project.name != "style-base") {
        project.tasks.create(name = "publishWebSite") {
            group = "atlasv-publish"
            publishWebSite(backendProjectName = "android-team-service")
        }
    }
}
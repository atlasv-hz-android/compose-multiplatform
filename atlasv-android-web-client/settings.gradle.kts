pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        kotlin("plugin.compose").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}
include(":home")
include(":common")
include(":page-home")
include(":page-perf-overview")
include(":page-upload-file")
include(":page-disk-report")
include(":page-log-viewer")
include(":style-base")

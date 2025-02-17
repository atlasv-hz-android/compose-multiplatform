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

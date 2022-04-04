rootProject.name = "compose-jb"

pluginManagement {
    resolutionStrategy {
        plugins {
            val kotlinVersion: String by extra
            val composeVersion: String by extra
            val consistentVersion: String by extra

            kotlin("multiplatform") version kotlinVersion
            kotlin("plugin.serialization") version kotlinVersion
            id("org.jetbrains.compose") version composeVersion
            id("com.palantir.consistent-versions") version consistentVersion
        }
    }

    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

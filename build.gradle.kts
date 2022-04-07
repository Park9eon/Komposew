import org.jetbrains.compose.jetbrainsCompose
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    idea
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("com.palantir.consistent-versions")
}

repositories {
    jetbrainsCompose()
    mavenCentral()
    google()
}

// https://youtrack.jetbrains.com/issue/KTOR-1084
tasks.withType<Kotlin2JsCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-Xir-per-module",
            "-Xir-property-lazy-initialization",
        )
    }
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                devtool = null
            }
            runTask {
                devServer?.open = false

                args += "--history-api-fallback"
            }
            webpackTask {
                report = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime")
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlinw("browser"))
                implementation(kotlinw("extensions"))
                implementation(kotlinw("history"))
                implementation(kotlinw("typescript"))
                // compose
                implementation(compose.web.core)
                implementation(compose.runtime)
                // npm
                implementation(devNpm("html-webpack-plugin", "^5.5.0"))
                // lit
                implementation(devNpm("lit", "^2.2.2"))
                // vaadin
                fun vaadin(component: String) = npm("@vaadin/vaadin-${component}", "^23.0.4")
                implementation(vaadin("button"))
                implementation(vaadin("date-time-picker"))
                implementation(vaadin("dialog"))
                // material
                fun material(component: String) = npm("@material/mwc-${component}", "^0.25.3")
                implementation(material("button"))
            }
        }
    }
}

afterEvaluate {
    yarn.lockFileDirectory = projectDir
}

val gradleWrapperVersion: String by extra

tasks.withType<Wrapper> {
    gradleVersion = gradleWrapperVersion
}

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
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
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
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
            }
            runTask {
                devServer?.open = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinx("html"))
                implementation(kotlinx("datetime"))
                implementation(kotlinx("serialization-json"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                // compose
                implementation(compose.web.core)
                implementation(compose.runtime)
                // https://github.com/JetBrains/kotlin-wrappers
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("react-router-dom"))

            }
        }
    }
}

afterEvaluate {
    yarn.lockFileDirectory = projectDir
}

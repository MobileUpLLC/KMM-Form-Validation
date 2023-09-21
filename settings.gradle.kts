rootProject.name = "KMM Form Validation"

pluginManagement {
    val kotlinVersion = "1.9.10"
    val androidPluginVersion = "8.1.1"

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform").version(kotlinVersion).apply(false)
        id("com.android.library").version(androidPluginVersion).apply(false)
        id("dev.icerock.mobile.multiplatform-resources").version("0.23.0").apply(false)
        id("com.android.application").version(androidPluginVersion).apply(false)
        id("org.jetbrains.kotlin.android").version(kotlinVersion).apply(false)
    }

    dependencyResolutionManagement {
        repositories {
            google()
            mavenCentral()
        }

        versionCatalogs {
            create("libs") {
                library("kotlin-gradle", "org.jetbrains.kotlin", "kotlin-gradle-plugin").version(kotlinVersion)
                library("android-gradle", "com.android.tools.build", "gradle").version(androidPluginVersion)

                version("coroutines", "1.7.3")
                library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
                library("coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")

                version("androidx-core", "1.12.0")
                library("androidx-core-ktx", "androidx.core", "core-ktx").versionRef("androidx-core")

                version("activity", "1.7.2")
                library("activity-ktx", "androidx.activity", "activity-ktx").versionRef("activity")
                library("activity-compose", "androidx.activity", "activity-compose").versionRef("activity")

                version("compose", "1.5.1")
                version("composeCompiler", "1.5.3")
                library("compose-foundation", "androidx.compose.foundation", "foundation").versionRef("compose")
                library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
                library("compose-material", "androidx.compose.material", "material").versionRef("compose")
                library("compose-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")

                version("moko-resources", "0.23.0")
                library("moko-resources", "dev.icerock.moko", "resources").versionRef("moko_resources")
                library("moko-resourcesCompose", "dev.icerock.moko", "resources-compose").versionRef("moko_resources")

                version("decompose", "2.0.2")
                version("essenty", "1.1.0")
                library("decompose", "com.arkivanov.decompose", "decompose").versionRef("decompose")
                library("decompose-compose", "com.arkivanov.decompose", "extensions-compose-jetpack").versionRef("decompose")
                library("essenty-lifecycle", "com.arkivanov.essenty", "lifecycle").versionRef("essenty")

                version("konfetti", "1.3.2")
                library("konfetti", "nl.dionsegijn", "konfetti").versionRef("konfetti")
            }
        }
    }
}
include(":kmm-form-validation")
include(":sample:androidSample")
include(":sample:sharedSample")
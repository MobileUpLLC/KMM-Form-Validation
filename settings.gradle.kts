rootProject.name = "SesameKMMForm"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    dependencyResolutionManagement {
//        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

        repositories {
            google()
            mavenCentral()
        }

        versionCatalogs {
            create("libs"){
                version("coroutines", "1.6.4")
                library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
                library("coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")

                version("androidx-core", "1.9.0")
                library("androidx-core-ktx", "androidx.core", "core-ktx").versionRef("androidx-core")

                version("activity", "1.6.1")
                library("activity-ktx","androidx.activity", "activity-ktx").versionRef("activity")
                library("activity-compose", "androidx.activity", "activity-compose").versionRef("activity")

                version("compose", "1.3.3")
                version("composeCompiler", "1.4.0")
                library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
                library("compose-material", "androidx.compose.material", "material").version("1.3.1")
                library("compose-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")

                version("moko-resources", "0.20.1")
                library("moko-resources-generator", "dev.icerock.moko", "resources-generator").versionRef("moko_resources")
                library("moko-resources", "dev.icerock.moko", "resources").versionRef("moko_resources")
                library("moko-resources-compose", "dev.icerock.moko", "resources-compose").versionRef("moko_resources")
                library("moko-graphics", "dev.icerock.moko", "graphics").version("0.9.0")

                version("decompose", "1.0.0-beta-04")
                library("decompose", "com.arkivanov.decompose", "decompose").versionRef("decompose")
                library("decompose-compose", "com.arkivanov.decompose", "extensions-compose-jetpack").versionRef("decompose")

                version("konfetti", "1.3.2")
                library("konfetti", "nl.dionsegijn", "konfetti").versionRef("konfetti")
            }
        }
    }
}
include(":shared")
include(":sample:androidSample")
include(":sample:sharedSample")
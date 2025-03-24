plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

apply {
    from("$rootDir/publish.gradle")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.moko.resources)
        }

        androidMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
    }
}

android {
    val minSdkVersion = libs.versions.minSdk.get().toInt()
    val compileSdkVersion = libs.versions.compileSdk.get().toInt()

    namespace = "ru.mobileup.kmm_form_validation"
    compileSdk = compileSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply {
    from("$rootDir/publish.gradle")
}

kotlin {
    android {
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
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.moko.resources)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.foundation)
                implementation(libs.compose.ui)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra

    namespace = "ru.mobileup.kmm_form_validation"
    compileSdk = targetSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
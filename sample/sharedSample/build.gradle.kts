plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedSample"
            export(project(":kmm-form-validation"))
            export(libs.moko.resources)
            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kmm-form-validation"))
                implementation(libs.coroutines.core)
                api(libs.moko.resources)
                api(libs.decompose)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
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

multiplatformResources {
    multiplatformResourcesPackage = "ru.mobileup.kmm_form_validation.sharedsample"
}


android {
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra

    namespace = "ru.mobileup.kmm_form_validation.sharedsample"
    compileSdk = targetSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
    }
    sourceSets.getByName("main") {
        res.srcDirs(
            // Workaround for Moko resources. See: https://github.com/icerockdev/moko-resources/issues/353#issuecomment-1179713713
            File(layout.buildDirectory.asFile.get(), "generated/moko/androidMain/res")
        )
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

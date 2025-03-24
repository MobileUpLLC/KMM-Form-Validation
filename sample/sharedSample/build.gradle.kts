plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.moko.resources)
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
        commonMain.dependencies {
            api(project(":kmm-form-validation"))
            implementation(libs.coroutines.core)
            api(libs.moko.resources)
            api(libs.decompose)
            api(libs.essenty.lifecycle)
        }
    }
}

multiplatformResources {
    resourcesPackage.set("ru.mobileup.kmm_form_validation.sharedsample")
}

android {
    val minSdkVersion = libs.versions.minSdk.get().toInt()
    val compileSdkVersion = libs.versions.compileSdk.get().toInt()

    namespace = "ru.mobileup.kmm_form_validation.sharedsample"
    compileSdk = compileSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

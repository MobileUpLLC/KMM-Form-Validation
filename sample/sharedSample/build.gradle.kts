import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    android {
        namespace = "ru.mobileup.kmm_form_validation.sharedsample"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedSample"
            binaryOption("bundleId", "ru.mobileup.kmm.form.sharedsample")
            export(project(":kmm-form-validation"))
            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":kmm-form-validation"))
            implementation(libs.coroutines.core)
            api(libs.decompose)
            api(libs.essenty.lifecycle)
        }
    }
}

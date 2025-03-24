plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    val minSdkVersion = libs.versions.minSdk.get().toInt()
    val targetSdkVersion = libs.versions.targetSdk.get().toInt()
    val compileSdkVersion = libs.versions.compileSdk.get().toInt()

    namespace = "ru.mobileup.kmm_form_validation.android_sample"
    compileSdk = compileSdkVersion

    defaultConfig {
        applicationId = "ru.mobileup.kmm_form_validation.android_sample"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material)
    implementation(libs.moko.resourcesCompose)
    implementation(libs.decompose)
    implementation(libs.decompose.compose)
    implementation(libs.konfetti)
    implementation(project(":sample:sharedSample"))
}
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedSample"
            export(project(":shared"))
            export(libs.moko.graphics)
            export(libs.moko.resources)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared"))
                implementation(libs.coroutines.core)
                api(libs.moko.resources)
                api(libs.moko.graphics)
                implementation(libs.decompose)
            }
        }
        val androidMain by getting
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
    multiplatformResourcesPackage = "ru.mobileup.sesame.kmm.sample"
    multiplatformResourcesClassName = "Res"
}


android {
    namespace = "ru.mobileup.sesame.kmm.sharedsample"
    compileSdk = 33
    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
}

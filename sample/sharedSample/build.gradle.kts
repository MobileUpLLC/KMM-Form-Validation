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
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                implementation(project(":shared"))
                implementation(libs.coroutines.core)
                implementation(libs.moko.resources)
                implementation(libs.moko.graphics)
                implementation(libs.decompose)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
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
        minSdk = 26
        targetSdk = 33
    }
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))

}
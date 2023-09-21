val minSdkVersion by extra(23)
val targetSdkVersion by extra(34)

buildscript {

    repositories {
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
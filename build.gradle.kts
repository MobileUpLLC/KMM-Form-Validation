buildscript {

    repositories {
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.moko.resources.generator)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
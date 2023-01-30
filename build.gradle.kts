
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("dev.icerock.mobile.multiplatform-resources").version("0.20.1").apply(false)
    id("com.android.application") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

buildscript {
    val kotlin_version by extra("1.8.0")

    repositories {
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath(libs.moko.resources.generator)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
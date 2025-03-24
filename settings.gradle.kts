rootProject.name = "KMM Form Validation"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":kmm-form-validation")
include(":sample:androidSample")
include(":sample:sharedSample")
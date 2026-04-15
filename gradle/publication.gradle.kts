import java.io.File
import org.gradle.api.GradleException
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import java.util.Properties

plugins.apply("maven-publish")
plugins.apply("signing")

// ./gradlew build
// ./gradlew publishAllPublicationsToCentralRepository --no-parallel
// curl -X POST \
//  -u '<user_name>:<token>' \
//  -H 'Content-Type: application/json' \
//  "https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/ru.mobileup?publishing_type=automatic"

val pomName = "KMM Form Validation"
val pomDescription = "Kotlin Multiplatform library to control and validate forms."
val pomUrl = "https://github.com/MobileUpLLC/KMM-Form-Validation"
val licenseName = "The MIT License"
val licenseUrl = "$pomUrl/blob/main/LICENSE"
val developerId = "MobileUp"
val developerName = "MobileUp"
val developerEmail = "dev@mobileup.ru"
val scmConnection = "scm:git:git://github.com/MobileUpLLC/KMM-Form-Validation.git"
val scmDeveloperConnection = "scm:git:ssh://git@github.com/MobileUpLLC/KMM-Form-Validation.git"
val scmUrl = "$pomUrl/tree/main"
val centralRepositoryUrl = "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.reader().use(::load)
    }
}

fun publicationProperty(name: String): String? {
    return providers.gradleProperty(name).orNull
        ?: localProperties.getProperty(name)
}

val centralUsername = publicationProperty("centralUsername")
val centralPassword = publicationProperty("centralPassword")
val signingKeyFilePath = publicationProperty("signingKeyFile")
val signingPassword = publicationProperty("signingPassword")

fun readSigningKey(signingKeyFilePath: String): String {
    val signingKeyFile = File(signingKeyFilePath)

    if (!signingKeyFile.isAbsolute) {
        throw GradleException(
            "Publishing to Maven Central requires signingKeyFile in local.properties to be an absolute path to a .asc file."
        )
    }

    if (!signingKeyFile.exists()) {
        throw GradleException(
            "Publishing to Maven Central requires signingKeyFile in local.properties to point to an existing .asc file."
        )
    }

    if (!signingKeyFile.isFile || !signingKeyFile.canRead()) {
        throw GradleException(
            "Publishing to Maven Central requires signingKeyFile in local.properties to point to a readable .asc file."
        )
    }

    return try {
        signingKeyFile.readText()
    } catch (error: Exception) {
        throw GradleException(
            "Failed to read signing key from signingKeyFile in local.properties. Provide an absolute path to a readable .asc file.",
            error
        )
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

extensions.configure<PublishingExtension> {
    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar)

        pom {
            name.set(pomName)
            description.set(pomDescription)
            url.set(pomUrl)

            licenses {
                license {
                    name.set(licenseName)
                    url.set(licenseUrl)
                }
            }

            developers {
                developer {
                    id.set(developerId)
                    name.set(developerName)
                    email.set(developerEmail)
                }
            }

            scm {
                connection.set(scmConnection)
                developerConnection.set(scmDeveloperConnection)
                url.set(scmUrl)
            }
        }
    }

    repositories {
        maven {
            name = "central"
            url = uri(centralRepositoryUrl)

            credentials {
                username = centralUsername
                password = centralPassword
            }
        }
    }
}

if (signingKeyFilePath != null && signingPassword != null) {
    val signingKey = readSigningKey(signingKeyFilePath)

    extensions.configure<SigningExtension> {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(extensions.getByType<PublishingExtension>().publications)
    }
}

gradle.taskGraph.whenReady {
    val isPublishingToCentral = allTasks.any { it.name.endsWith("ToCentralRepository") }
    if (!isPublishingToCentral) return@whenReady

    if (centralUsername.isNullOrBlank() || centralPassword.isNullOrBlank()) {
        throw GradleException(
            "Publishing to Maven Central requires centralUsername and centralPassword in local.properties."
        )
    }

    if (signingKeyFilePath.isNullOrBlank() || signingPassword.isNullOrBlank()) {
        throw GradleException(
            "Publishing to Maven Central requires signingKeyFile and signingPassword in local.properties."
        )
    }
}

// Workaround for https://github.com/gradle/gradle/issues/26091
tasks.withType<AbstractPublishToMaven>().configureEach {
    mustRunAfter(tasks.withType<Sign>())
}

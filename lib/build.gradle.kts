plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

group="dev.tocraft.crafted"
version="0.1"

base {
    archivesName="cli"
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
}

testing {
    suites {
        // Configure the built-in test suite
        @Suppress("UnstableApiUsage") val test by getting(JvmTestSuite::class) {
            // Use JUnit4 test framework
            useJUnit("4.13.2")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}


extensions.configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "cli"
            version = project.version as String
            from(components["java"])
        }
    }
    repositories {
        if (System.getenv("MAVEN_PASS") != null) {
            maven("https://maven.tocraft.dev/public") {
                name = "ToCraftMavenPublic"
                credentials {
                    username = "tocraft"
                    password = System.getenv("MAVEN_PASS")
                }
            }
        }
    }
}

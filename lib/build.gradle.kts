plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

group="dev.tocraft"
version="0.4"

base {
    archivesName="cli"
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

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.20-Beta2"
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.graalvm.nativeimage:svm:24.2.2")
    compileOnly("com.google.protobuf:protobuf-java:4.28.0")
    implementation("org.reflections:reflections:0.10.2")

}

java {
    withJavadocJar()
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ricantech/protobuf-graalvm-feature")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.ricantech"
            artifactId = "protobuf-graalvm-feature"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

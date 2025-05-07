plugins {
    application
    id("java")
    id("maven-publish")
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Replace this with specific versions if `libs.guava` isn't configured
    implementation("com.google.guava:guava:33.0.0-jre")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // This is the correct way to assign main class
    mainClass.set("org.example.App")
}

// Include the main class in the manifest (needed for 'java -jar')
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.App"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://trial9rff7n.jfrog.io/artifactory/api/maven/libs-release-local")
            credentials {
                username = findProperty("artifactory_user") as String? ?: ""
                password = findProperty("artifactory_password") as String? ?: ""
            }
        }
    }
}

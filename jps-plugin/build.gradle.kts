plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.adamweigold.jibx"
version = "0.5-SNAPSHOT"

intellij {
    version.set("2024.1")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("com.intellij.java"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jibx:jibx-bind:1.4.2")

    implementation("org.apache.maven:maven-model:3.9.6")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("com.google.truth:truth:1.4.2")
}

tasks{

    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    jar {
        val dependencies = configurations
                .runtimeClasspath
                .get()
                .map(::zipTree) // OR .map { zipTree(it) }
        from(dependencies)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("${project.name}.jar")
    }
}

tasks.test {
    useJUnitPlatform()
}
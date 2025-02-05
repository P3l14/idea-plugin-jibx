plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "com.adamweigold.jibx"
version = "0.7-SNAPSHOT"

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            untilBuild = provider { null }
        }
    }
}

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation("org.jibx:jibx-bind:1.4.2")
    implementation("org.apache.bcel:bcel:6.8.2")

    implementation("org.apache.maven:maven-model:3.9.6")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("com.google.truth:truth:1.4.2")
    intellijPlatform {
        intellijIdeaCommunity("2024.3.2.1")
        bundledPlugin("com.intellij.java")
    }
}

tasks{

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
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
plugins {
    kotlin("jvm") version "1.6.10"
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.18.0"
    `maven-publish`
}
group = "org.omegat"
version = "1.5.3"

tasks.compileJava {
    options.release.set(8)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
}

pluginBundle {
    vcsUrl = "https://github.com/miurahr/gradle-omegat.git"
    website = "https://github.com/miurahr/gradle-omegat-plugin"
    description = "OmegaT plugin for Gradle"
    tags = listOf("OmegaT")
    plugins.create("omegatPlugin") {
        id = "org.omegat.gradle"
        displayName = "OmegaT plugin"
    }
}

gradlePlugin {
    plugins.create("omegatPlugin") {
        id = "org.omegat.gradle"
        implementationClass = "org.omegat.gradle.OmegatPlugin"
    }
}

publishing {
  publications.withType(MavenPublication::class).all {
    if (name == "pluginMaven") {
      artifactId = "gradle-omegat-plugin"
    }
  }
}

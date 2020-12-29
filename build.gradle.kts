plugins {
    kotlin("jvm") version "1.3.72"
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.12.0"
    `maven-publish`
}
group = "org.omegat"
version = "1.3.0-SNAPSHOT"

tasks.compileJava {
    options.release.set(8)
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation(localGroovy())
}

pluginBundle {
    vcsUrl = "https://github.com/miurahr/gradle-omegat.git"
    website = "https://github.com/miurahr/gradle-omegat"
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
plugins {
    kotlin("jvm") version "1.7.10"
    id("java-gradle-plugin")  // for plugin authoring
    id ("maven-publish")  // for metadata
    id("com.gradle.plugin-publish") version "1.0.0"  // for publish to plugin portal
}

group = "org.omegat"
version = "1.5.7"

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
    vcsUrl = "https://github.com/miurahr/gradle-omegat-plugin.git"
    website = "https://github.com/miurahr/gradle-omegat-plugin"
    tags = listOf("OmegaT", "translation", "plugin development")
}

gradlePlugin {
    plugins.create("omegatPlugin") {
        id = "org.omegat.gradle"
        implementationClass = "org.omegat.gradle.OmegatPlugin"
        displayName = "OmegaT"
        description = "OmegaT plugin for Gradle allow users to run translation tasks with gradle," +
                " which intend to help task automation on CI/CD platform such as Github actions, or travis-ci." +
                "The plugin also provide a dependency management, manifest generation, omegat runner with debugger" +
                " and fat-jar generation for omegat-plugin development."
    }
}

// publish plugin into local repository
publishing {
  publications.withType(MavenPublication::class).all {
    if (name == "pluginMaven") {
      artifactId = "gradle-omegat-plugin"
    }
  }
}

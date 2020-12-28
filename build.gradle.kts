plugins {
    kotlin("jvm") version "1.4.21"
    groovy
    `groovy-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.12.0"
    `maven-publish`
}
group = "org.omegat"
version = "1.2.4"

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

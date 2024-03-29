package org.omegat.gradle.config

import org.gradle.api.Project
import org.omegat.gradle.OmegatPlugin
import java.util.Properties

class DefaultModule(project: Project) {
    private val props = Properties()

    init {
        props.load(OmegatPlugin::class.java.getResourceAsStream("omegat.properties"))
        project.repositories.mavenCentral()
    }

    fun getDependencies(version: String?): List<String> {
        val omtVersion = version ?: props.getProperty("omegatVersion").toString()
        val vldockingVersion = props.getProperty("vldockingVersion").toString()
        return listOf("org.omegat:omegat:${omtVersion}", "org.omegat:vldocking:${vldockingVersion}")
    }
}
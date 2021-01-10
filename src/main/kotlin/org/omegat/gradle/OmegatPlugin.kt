package org.omegat.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.omegat.gradle.config.PluginExtension
import org.omegat.gradle.task.setupOmegatConfig
import org.omegat.gradle.task.setupOmegatTasks


class OmegatPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("omegat", PluginExtension::class.java, project).let {
            project.setupOmegatConfig(it)
            project.setupOmegatTasks(it)
        }
    }
}

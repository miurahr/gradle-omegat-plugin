package org.omegat.gradle.task

import org.gradle.api.Project
import org.omegat.gradle.config.OmegatPluginExtension

fun Project.setupOmegatTasks(extension: OmegatPluginExtension) {
    tasks.register("translate", TranslateTask::class.java) { task ->
        task.projectDir = extension.projectDir.toString()
    }
    tasks.register("runOmegaT", RunOmegatTask::class.java) { task ->
    }
    if (extension.debugPort != null) {
        tasks.register("debugOmegaT", DebugOmegatTask::class.java) { task ->
            task.debugPort = extension.debugPort
        }
    }
}

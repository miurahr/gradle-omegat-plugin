package org.omegat.gradle.config

import org.gradle.api.Project
import org.omegat.gradle.task.DebugTask
import org.omegat.gradle.task.TranslateTask
import org.omegat.gradle.task.RunTask


fun Project.setupOmegatTasks(extension: OmegatPluginExtension) {
    tasks.register("translate", TranslateTask::class.java) { task ->
        task.rootDir = project.rootDir.toString()
    }
    tasks.register("runOmegaT", RunTask::class.java) { task ->
        task.rootDir = project.rootDir.toString()
    }
    if (extension.debugPort != null) {
        tasks.register("debugOmegaT", DebugTask::class.java) { task ->
            task.rootDir = project.rootDir.toString()
            task.debugPort = extension.debugPort
        }
    }
}

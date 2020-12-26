package org.omegat.gradle.config

import org.gradle.api.Project
import org.omegat.gradle.task.OmegatTranslateTask

fun Project.setupOmegatTasks() {
    tasks.register("omegatTranslate", OmegatTranslateTask::class.java) { task ->
        task.rootDir = project.rootDir.toString()
    }
}

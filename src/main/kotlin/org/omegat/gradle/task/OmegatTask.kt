package org.omegat.gradle.task

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction


@CacheableTask
open class OmegatTask : JavaExec() {
    init {
        group = "org.omegat"
        main = "org.omegat.Main"
    }

    @TaskAction
    override fun exec() {
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        super.exec()
    }
}
package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class OmegatTranslateTask : OmegatTask() {
    @Input
    var rootDir: String = "."

    @TaskAction
    override fun exec() {
        args = listOf(rootDir, "--mode=console-translate")
        super.exec()
    }
}
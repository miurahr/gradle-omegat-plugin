package org.omegat.gradle

import org.gradle.api.tasks.TaskAction

open class OmegatTranslateTask : OmegatTask() {
    var rootDir: String = "."

    @TaskAction
    override fun exec() {
        args = listOf(rootDir, "--mode=console-translate")
        super.exec()
    }
}
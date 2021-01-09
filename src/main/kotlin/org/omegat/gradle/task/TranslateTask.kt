package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class TranslateTask : BaseOmegatTask() {

    @TaskAction
    override fun exec() {
        args = getProperties().apply {
            add("--mode=console-translate")
        }
        super.exec()
    }
}
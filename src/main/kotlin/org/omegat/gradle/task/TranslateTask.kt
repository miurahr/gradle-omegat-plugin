package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class TranslateTask : BaseTask() {
    @TaskAction
    override fun exec() {
        argList.apply {
            add("--mode=console-translate")
        }
        super.exec()
    }
}
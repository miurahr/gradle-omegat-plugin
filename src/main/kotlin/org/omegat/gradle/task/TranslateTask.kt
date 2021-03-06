package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class TranslateTask : BaseTask() {
    @TaskAction
    override fun exec() {
        argList.apply {
            if (projectDir != null) {
                add(projectDir!!)
            } else {
                add(project.rootDir.toString())
            }
            add("--mode=console-translate")
        }
        super.exec()
    }
}
package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class RunOmegatTask : BaseOmegatTask()  {
    @TaskAction
    override fun exec() {
        args = getArgList()
        super.exec()
    }
}

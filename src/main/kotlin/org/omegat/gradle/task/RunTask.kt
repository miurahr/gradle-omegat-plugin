package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction
import java.io.File

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        args = getArgList().apply {
            add("--config-dir=${File(project.buildDir, "tmp/omegat/")}")
        }
        super.exec()
    }
}

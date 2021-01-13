package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction
import java.io.File

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        argList.apply {
            add("--config-dir=${File(project.buildDir, "omegat")}")
        }
        super.exec()
    }
}

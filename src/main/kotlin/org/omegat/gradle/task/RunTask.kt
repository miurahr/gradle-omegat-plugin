package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction
import java.io.File

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        argList.apply {
            projectDir?.let { add(it) }
            add("--config-dir=${File(project.buildDir, "omegat")}")
        }
        super.exec()
    }
}

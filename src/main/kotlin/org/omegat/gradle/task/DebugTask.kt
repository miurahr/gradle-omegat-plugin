package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class DebugTask : RunTask() {
     @Input
     var debugPort: Int? = null

     @TaskAction
     override fun exec() {
          jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$debugPort")
          super.exec()
     }
}
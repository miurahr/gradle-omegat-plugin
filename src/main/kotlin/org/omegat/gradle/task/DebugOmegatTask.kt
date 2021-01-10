package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class DebugOmegatTask : BaseOmegatTask() {
     @Input
     var debugPort: Int? = null

     @TaskAction
     override fun exec() {
          args = getArgList()
          jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$debugPort")
          super.exec()
     }
}
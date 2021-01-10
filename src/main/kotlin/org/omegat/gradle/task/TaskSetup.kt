package org.omegat.gradle.task

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.omegat.gradle.config.OmegatPluginExtension
import java.io.File

fun Project.setupOmegatTasks(extension: OmegatPluginExtension, javaConvention: JavaPluginConvention?) {
    tasks.register("translate", TranslateTask::class.java) { task ->
        task.projectDir = extension.projectDir.toString()
    }
    if (javaConvention != null) {
        val mainSourceSet = javaConvention.sourceSets.getByName("main")
        tasks.register("runOmegaT", RunOmegatTask::class.java) { task ->
            task.doFirst {
                project.copy {
                    it.from(mainSourceSet.output)
                    it.into(File(project.buildDir, "tmp/omegat/plugins/"))
                }
            }
        }
        if (extension.debugPort != null) {
            tasks.register("debugOmegaT", DebugOmegatTask::class.java) { task ->
                task.debugPort = extension.debugPort
                task.doFirst {
                    project.copy {
                        it.from(mainSourceSet.output)
                        it.into(File(project.buildDir, "tmp/omegat/plugins/"))
                    }
                }
            }
        }
    }
}

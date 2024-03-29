package org.omegat.gradle.task

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.omegat.gradle.config.DefaultModule
import org.omegat.gradle.config.PluginExtension
import java.io.File


fun Project.setupOmegatTasks(extension: PluginExtension) {
    tasks.register("translate", TranslateTask::class.java) { task ->
        task.projectDir = extension.projectDir.toString()
    }
    tasks.register("runOmegaT", RunTask::class.java) { task ->
        task.projectDir = extension.projectDir.toString()
    }
    tasks.register("debugOmegaT", DebugTask::class.java) { task ->
        task.projectDir = extension.projectDir.toString()
    }
    afterEvaluate {
        if (extension.pluginClass != null) {
            val jarTask = project.tasks.withType(Jar::class.java).getByName("jar")
            tasks.withType(RunTask::class.java).getByName("runOmegaT").apply {
                dependsOn(jarTask)
            }
            tasks.withType(DebugTask::class.java).getByName("debugOmegaT").apply {
                debugPort = extension.debugPort
                dependsOn(jarTask)
            }
            jarTask.outputs.upToDateWhen { false }
            jarTask.doFirst { task ->
                if (extension.pluginClass != null) {
                    jarTask.manifest.attributes(extension.manifest.createOmegatPluginJarManifest(extension))
                }
                jarTask.from(
                    task.project.configurations.getByName("packIntoJar").files.map { file ->
                        if (file.isDirectory) {
                            project.fileTree(file)
                        } else {
                            project.zipTree(file)
                        }.matching {
                            extension.packIntoJarFileFilter.invoke(it)
                        }
                    }
                )
            }
            jarTask.doLast { task ->
                project.copy {
                    it.from(task.outputs.files)
                    it.into(File(project.buildDir, "omegat/plugins/"))
                }
            }
        }
    }
}

fun Project.setupOmegatConfig(extension: PluginExtension) {
    project.configurations.run {
        val config = create("packIntoJar")
        config.setVisible(false).setTransitive(true).description = "The OmegaT configuration for this project."
        create("omegat")
    }
    afterEvaluate {
        val deps = DefaultModule(project).getDependencies(extension.version)
        project.configurations.run {
            getByName("omegat").apply {
                for (dep in deps) {
                    dependencies.add(project.dependencies.create(dep))
                }
            }
        }
        if (extension.pluginClass != null) {
            project.configurations.run {
                getByName("implementation").apply {
                    extendsFrom(getByName("packIntoJar"))
                }
                deps.forEach { dep ->
                    getByName("implementation").apply {
                        dependencies.add(project.dependencies.create(dep))
                    }
                    getByName("testImplementation").apply {
                        dependencies.add(project.dependencies.create(dep))
                    }
                }
            }
        }
    }
}
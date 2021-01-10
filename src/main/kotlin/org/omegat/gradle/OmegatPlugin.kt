package org.omegat.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.jvm.tasks.Jar
import org.omegat.gradle.config.DefaultOmegatModule
import org.omegat.gradle.config.OmegatPluginExtension
import org.omegat.gradle.task.setupOmegatTasks
import java.io.File


class OmegatPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("omegat", OmegatPluginExtension::class.java, project)
        project.configurations.run {
            val config = create("packIntoJar")
            config.setVisible(false).setTransitive(true).setDescription("The OmegaT configuration for this project.")
            create("omegat")
        }
        with(project) {
            val javaConvention = project.convention.findPlugin(JavaPluginConvention::class.java)
            project.setupOmegatTasks(extension, javaConvention)
            afterEvaluate {
                val deps = DefaultOmegatModule(project).getDependencies(extension.version)
                project.configurations.run {
                    getByName("omegat").apply {
                        for (dep in deps) {
                            dependencies.add(project.dependencies.create(dep))
                        }
                    }
                }
                if (!File(extension.projectDir, "omegat.project").exists()) {
                    val jarTask = project.tasks.withType(Jar::class.java).getByName("jar")
                    jarTask.outputs.upToDateWhen { false }
                    jarTask.doFirst { task ->
                        if (extension.pluginClass != null) {
                            val atts: Map<String, String?> = mapOf("OmegaT-Plugins" to extension.pluginClass)
                            jarTask.manifest.attributes(atts)
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
    }
}

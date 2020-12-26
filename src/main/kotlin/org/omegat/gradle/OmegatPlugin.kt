package org.omegat.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import java.io.File
import java.util.*


class OmegatPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.run {
            create("omegat", OmegatPluginExtension::class.java)
        }
        project.configurations.run {
            val config = create("packIntoJar")
            config.setVisible(false).setTransitive(true).setDescription("The OmegaT configuration for this project.")
            create("omegat")
        }
        with(project) {
            tasks.register("omegatTranslate", OmegatTranslateTask::class.java) { task ->
                task.rootDir = project.rootDir.toString()
            }

            afterEvaluate {
                val props = Properties()
                props.load(OmegatPlugin::class.java.getResourceAsStream("omegat.properties"))
                project.repositories.jcenter()
                project.repositories.apply {
                    maven {
                        it.setUrl(props.getProperty("mavenRepositoryUrl"))
                    }
                }
                val group = props.getProperty("omegatGroup")
                val artifact = props.getProperty("omegatArtifact")
                val version = extension.version ?: props.getProperty("omegatVersion")
                val dep = "${group}:${artifact}:${version}"
                project.configurations.run {
                    getByName("omegat").apply {
                        dependencies.add(project.dependencies.create(dep))
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
                        val implementation = getByName("implementation")
                        getByName("packIntoJar").apply {
                            implementation.extendsFrom(this)
                            implementation.dependencies.add(project.dependencies.create(dep))
                        }
                        getByName("testImplementation").run {
                            dependencies.add(project.dependencies.create(dep))
                        }
                    }
                }
            }
        }
    }
}
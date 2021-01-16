package org.omegat.gradle.task

import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.work.InputChanges
import java.io.File
import javax.inject.Inject


/**
 * Build plugin with custom version of OmegaT.
 */
open class CustomOmTVersionCompile
    @Inject
    constructor(private val customVersionProvider: () -> String, private val sourceSet: SourceSet): JavaCompile() {

    private lateinit var customVersion: String

    @TaskAction
    override fun compile(inputs: InputChanges) {
        classpath += project.configurations.getByName(sourceSet.compileClasspathConfigurationName).copy().excludeOmegaT()
        val customOmegaT = project.dependencies.createOmegaT(customVersion)
        classpath += project.configurations.detachedConfiguration(customOmegaT)
        super.compile(inputs)
    }

    init {
        project.afterEvaluate {
            source(sourceSet.java)
            customVersion = customVersionProvider.invoke()
            description = "Compile the OmegaT plugin against $customVersion"
            destinationDir = File(project.buildDir, "classes/java/${sourceSet.name}_$customVersion")
        }
    }
}

package org.omegat.gradle.config

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.util.PatternFilterable
import java.io.File

open class OmegatPluginExtension(val project: Project) {
    var projectDir: String? = null
    var version: String? = null
    var pluginClass: String? = null

    var tmpOmegatPrefDir = File(project.buildDir, ".omegat/")
    var pluginDir = File(tmpOmegatPrefDir, "plugins")

    val initialPreferences: Property<String> = project.objects.property(String::class.java).convention("")

    /**
     * Set the [packIntoJarFileFilter] with a Groovy [Closure]
     */
    var packIntoJarFileFilter: (PatternFilterable) -> PatternFilterable = { it.exclude("META-INF/**/*") }
    fun packIntoJarFileFilter(closure: Closure<PatternFilterable>) {
        packIntoJarFileFilter = { closure.call(it) }
    }
}
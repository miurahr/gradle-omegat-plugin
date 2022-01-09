package org.omegat.gradle.config

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternFilterable


open class PluginExtension(val project: Project) {
    var pluginName: String? = null
    var projectDir: String? = null
    var version: String? = null
    var pluginClass: String? = null
    var debugPort: Int? = null

    val manifest: OmegatManifest = OmegatManifest(project)

    /**
     * Set the [packIntoJarFileFilter] with a Groovy [Closure]
     */
    var packIntoJarFileFilter: (PatternFilterable) -> PatternFilterable = { it.exclude("META-INF/**/*") }
    fun packIntoJarFileFilter(closure: Closure<PatternFilterable>) {
        packIntoJarFileFilter = { closure.call(it) }
    }
}

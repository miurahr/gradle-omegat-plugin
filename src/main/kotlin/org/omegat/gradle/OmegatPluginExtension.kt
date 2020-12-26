package org.omegat.gradle

import groovy.lang.Closure
import org.gradle.api.tasks.util.PatternFilterable

open class OmegatPluginExtension {
    var projectDir: String? = null
    var version: String? = null
    var pluginClass: String? = null

    /**
     * Set the [packIntoJarFileFilter] with a Groovy [Closure]
     */
    var packIntoJarFileFilter: (PatternFilterable) -> PatternFilterable = { it.exclude("META-INF/**/*") }
    fun packIntoJarFileFilter(closure: Closure<PatternFilterable>) {
        packIntoJarFileFilter = { closure.call(it) }
    }
}
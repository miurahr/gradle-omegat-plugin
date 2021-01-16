package org.omegat.gradle.config

import org.gradle.api.Project
import java.net.URL

class OmegatManifest(private val project: Project, val extension: PluginExtension) {

    object Attribute {
        const val AUTHOR = "Author"
        const val PLUGIN_DESCRIPTION = "Plugin-Description"
        const val PLUGIN_MAIN_CLASS = "OmegaT-Plugins"
        const val PLUGIN_WEBSITE = "Plugin-Link"
        const val PLUGIN_VERSION = "Plugin-Version"
        const val PLUGIN_MIN_VERSION = "Plugin-Min-Version"
    }

    var author: String? = project.findProperty("plugin.author")?.toString()
    var description: String? = project.findProperty("plugin.description")?.toString()
    var website: URL? =
        if (project.hasProperty("plugin.link")) URL(project.findProperty("plugin.link").toString()) else null
    var minVersion: String? = extension.minVersion
    var mainClass: String? = extension.pluginClass

    fun createOmegatPluginJarManifest(): Map<String, String> {

        val manifestAtts: MutableMap<String,String?> = mutableMapOf(
            Attribute.AUTHOR to author,
            Attribute.PLUGIN_MIN_VERSION to minVersion,
            Attribute.PLUGIN_MAIN_CLASS to mainClass,
            Attribute.PLUGIN_DESCRIPTION to description,
            Attribute.PLUGIN_VERSION to project.version.toString(),
            Attribute.PLUGIN_WEBSITE to website?.toString()
        )
        return manifestAtts.mapNotNull { entry ->
          entry.value?.let { Pair(entry.key, it) } // only return entries with not-null value
        }.toMap()
    }
}
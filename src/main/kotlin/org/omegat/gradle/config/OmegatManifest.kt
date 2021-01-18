package org.omegat.gradle.config

import org.gradle.api.Project
import java.net.URL

class OmegatManifest(private val project: Project, private val extension: PluginExtension) {

    object Attribute {
        const val PLUGIN_NAME = "Plugin-Name"
        const val PLUGIN_AUTHOR = "Plugin-Author"
        const val PLUGIN_VERSION = "Plugin-Version"
        const val PLUGIN_DESCRIPTION = "Plugin-Description"
        const val PLUGIN_MAIN_CLASS = "OmegaT-Plugins"
        const val PLUGIN_WEBSITE = "Plugin-Link"
    }

    var author: String? = project.findProperty("plugin.author")?.toString()
    var description: String? = project.findProperty("plugin.description")?.toString()
    var website: URL? =
        if (project.hasProperty("plugin.link"))
            URL(project.findProperty("plugin.link").toString().removeSurrounding("\""))
        else null

    fun createOmegatPluginJarManifest(): Map<String, String> {

        val manifestAtts: MutableMap<String,String?> = mutableMapOf(
            Attribute.PLUGIN_NAME to extension.pluginName,
            Attribute.PLUGIN_AUTHOR to author,
            Attribute.PLUGIN_MAIN_CLASS to extension.pluginClass,
            Attribute.PLUGIN_DESCRIPTION to description,
            Attribute.PLUGIN_VERSION to project.version.toString(),
        Attribute.PLUGIN_WEBSITE to website?.toString()
        )
        return manifestAtts.mapNotNull { entry ->
          entry.value?.let { Pair(entry.key, it) } // only return entries with not-null value
        }.toMap()
    }
}
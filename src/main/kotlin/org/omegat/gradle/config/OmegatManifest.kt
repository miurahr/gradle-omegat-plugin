package org.omegat.gradle.config

import org.gradle.api.Project
import java.net.URL
import java.util.GregorianCalendar


class OmegatManifest(private val project: Project) {

    object Attribute {
        const val PLUGIN_NAME = "Plugin-Name"
        const val PLUGIN_AUTHOR = "Plugin-Author"
        const val PLUGIN_VERSION = "Plugin-Version"
        const val PLUGIN_DESCRIPTION = "Plugin-Description"
        const val PLUGIN_MAIN_CLASS = "OmegaT-Plugins"
        const val PLUGIN_WEBSITE = "Plugin-Link"
        const val PLUGIN_CATEGORY = "Plugin-Category"
        const val PLUGIN_DATE = "Plugin-Date"
        const val CREATED_BY = "Created-By"
    }

    var name: String? = project.findProperty("plugin.name")?.toString()
    var author: String? = project.findProperty("plugin.author")?.toString()
    var description: String? = project.findProperty("plugin.description")?.toString()
    var category: String? = project.findProperty("plugin.category")?.toString()
    var website: URL? =
        if (project.hasProperty("plugin.link"))
            URL(project.findProperty("plugin.link").toString().removeSurrounding("\""))
        else null

    fun createOmegatPluginJarManifest(extension: PluginExtension): Map<String, String> {

        val manifestAtts: MutableMap<String,String?> = mutableMapOf(
            Attribute.PLUGIN_NAME to if (name != null) name else extension.pluginName,
            Attribute.PLUGIN_AUTHOR to author,
            Attribute.PLUGIN_MAIN_CLASS to extension.pluginClass,
            Attribute.PLUGIN_DESCRIPTION to description,
            Attribute.PLUGIN_VERSION to project.version.toString(),
            Attribute.PLUGIN_WEBSITE to website?.toString(),
            Attribute.PLUGIN_CATEGORY to category,
            Attribute.CREATED_BY to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")})",
            Attribute.PLUGIN_DATE to String.format("%1\$tY-%1\$tm-%1\$tdT%1\$tH:%1\$tM:%1\$tS%1\$tz", GregorianCalendar())
        )
        return manifestAtts.mapNotNull { entry ->
          entry.value?.let { Pair(entry.key, it) } // only return entries with not-null value
        }.toMap()
    }
}
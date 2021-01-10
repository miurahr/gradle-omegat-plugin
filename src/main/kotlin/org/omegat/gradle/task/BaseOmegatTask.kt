package org.omegat.gradle.task

import org.gradle.api.internal.properties.GradleProperties
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction
import java.io.File


@CacheableTask
open class BaseOmegatTask : JavaExec() {
    init {
        group = "org.omegat"
        main = "org.omegat.Main"
    }

    @Input
    var rootDir: String = "."

    @Input
    var properties: GradleProperties? = null

    protected fun getProperties(): MutableList<String> {
        var argList: MutableList<String> = mutableListOf(rootDir)
        if (hasProperty("user.language")) {
            argList.add("-Duser.language=" + property("user.language"))
            if (hasProperty("user.country")) {
                argList.add("-Duser.country=" + property("user.country"))
            }
        }
        if (hasProperty("http.proxyHost")) {
            argList.add("-Dhttp.proxyHost=" + property("http.proxyHost"))
        }
        argList.add("--config-dir=${File(project.buildDir, "tmp/omegat/")}")
        return argList
    }

    @TaskAction
    override fun exec() {
        args = getProperties()
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        super.exec()
    }
}